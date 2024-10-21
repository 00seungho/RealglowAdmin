package ac.kopo.kr.realglowadmin.repository.search;

import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.entity.Notice;
import ac.kopo.kr.realglowadmin.entity.QAdmin;
import ac.kopo.kr.realglowadmin.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SearchNoticeRepositoryImpl extends QuerydslRepositorySupport implements SearchNoticeRepository{
    public SearchNoticeRepositoryImpl() {
        super(Notice.class);
    }
    public Page<Notice> searchPage(String type, String keyword, Pageable pageable){
        QNotice notice = QNotice.notice;
        QAdmin admin = QAdmin.admin;
        JPQLQuery<Notice> jpqlQuery = from(notice);

        jpqlQuery.leftJoin(admin).on(notice.writer.eq(admin));
        JPQLQuery<Notice> notices = jpqlQuery.select(notice);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(notice.title.contains(keyword)); // company name 검색
                        break;
                    case "c":
                        conditionBuilder.or(notice.content.contains(keyword)); // item name 검색
                        break;
                    case "w":
                        conditionBuilder.or(admin.username.contains(keyword)); // color name 검색
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }//end if

        notices.where(booleanBuilder);
        notices.groupBy(notice);
        notices.offset(pageable.getOffset());
        notices.limit(pageable.getPageSize());

        List<Notice> result = notices.fetch();
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Notice.class, "notice");
            notices.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        long count = notices.fetchCount();
        return new PageImpl<Notice>(result, pageable, count);
    };
}
