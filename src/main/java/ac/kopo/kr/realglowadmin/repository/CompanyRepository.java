package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.entity.Company;
import ac.kopo.kr.realglowadmin.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c from Company c where c.id = :id")
    Company getCompany(@Param("id") Long id);
    @Query("select i from Company i")
    Page<Company> getCompanysWithDetails(Pageable pageable);
}