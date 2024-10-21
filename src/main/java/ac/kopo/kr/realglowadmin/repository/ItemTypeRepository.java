package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.entity.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    @Query("select i from ItemType i")
    Page<ItemType> getItemTypesWithDetails(Pageable pageable);

    Page<ItemType> findByTypeNameContaining(String keyword, Pageable pageable);
}
