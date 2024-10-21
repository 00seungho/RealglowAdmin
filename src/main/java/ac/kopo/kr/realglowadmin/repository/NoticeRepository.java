package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.entity.ItemType;
import ac.kopo.kr.realglowadmin.entity.Notice;
import ac.kopo.kr.realglowadmin.repository.search.SearchNoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long>, SearchNoticeRepository {
    @Query("select i from Notice i")
    Page<Notice> getNoticesWithDetails(Pageable pageable);
}
