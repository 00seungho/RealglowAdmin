package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class adminregTEST {
    @Autowired
    private AdminService adminService;
    @Test
    public void testSaveAdmin() {
        // 주어진 데이터로 Admin 엔티티 생성
        AdminDTO adminDTO = AdminDTO.builder()
                .password("coffice2024")
                .username("realglowadmin")
                .role("ADMIN")
                .build();
        // Admin 엔티티 저장
        Admin savedAdmin = adminService.saveAdmin(adminDTO);
    }
}
