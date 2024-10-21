package ac.kopo.kr.realglowadmin.repository.search;

import ac.kopo.kr.realglowadmin.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchItemRepository {
    Page<Item> searchPage(String type, String keyword, Pageable pageable);
}
