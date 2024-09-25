package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsername(String username);
}