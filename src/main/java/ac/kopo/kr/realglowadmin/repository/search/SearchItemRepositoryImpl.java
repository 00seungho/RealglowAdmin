package ac.kopo.kr.realglowadmin.repository.search;

import ac.kopo.kr.realglowadmin.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

@Log4j2
public class SearchItemRepositoryImpl extends QuerydslRepositorySupport implements SearchItemRepository{

    public SearchItemRepositoryImpl() {
        super(Item.class);
    }

    public Page<Item> searchPage(String type, String keyword, Pageable pageable) {

        QItem item = QItem.item;
        QCompany company = QCompany.company;
        QItemType itemType = QItemType.itemType;

        JPQLQuery<Item> jpqlQuery = from(item);
        jpqlQuery.leftJoin(company).on(item.company.eq(company));
        jpqlQuery.leftJoin(itemType).on(item.itemType.eq(itemType));

        JPQLQuery<Item> items = jpqlQuery.select(item);

        // BooleanBuilder로 검색 조건 설정
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "c":
                        conditionBuilder.or(company.name.contains(keyword)); // company name 검색
                        break;
                    case "i":
                        conditionBuilder.or(item.itemName.contains(keyword)); // item name 검색
                        break;
                    case "o":
                        conditionBuilder.or(item.colorName.contains(keyword)); // color name 검색
                        break;
                    case "t":
                        conditionBuilder.or(itemType.typeName.contains(keyword));
                }
            }
            booleanBuilder.and(conditionBuilder);
        }//end if

        items.where(booleanBuilder);
        items.groupBy(item);
        items.offset(pageable.getOffset());
        items.limit(pageable.getPageSize());

        List<Item> result = items.fetch();
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Item.class, "item");
            items.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        long count = items.fetchCount();
        return new PageImpl<Item>(result, pageable, count);
    }
}