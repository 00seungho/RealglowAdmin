package ac.kopo.kr.realglowadmin.repository.search;

import ac.kopo.kr.realglowadmin.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNoticeRepository {
    Page<Notice> searchPage(String type, String keyword, Pageable pageable);
}
