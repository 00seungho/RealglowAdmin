package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.id = :id")
    Item getItemWithDetails(@Param("id") Long id);

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.company c LEFT JOIN FETCH i.itemType it")
    Page<Item> getItemsWithDetails(Pageable pageable);

    List<Item> findByCompanyId(Long companyId);
    List<Item> findByItemTypeId(Long itemTypeId);
}
