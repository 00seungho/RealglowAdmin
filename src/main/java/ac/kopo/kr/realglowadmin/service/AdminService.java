package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.entity.Admin;
import ac.kopo.kr.realglowadmin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // AdminDTO를 Admin 엔티티로 변환
    public Admin dtoToEntity(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setId(adminDTO.getId());
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword())); // 비밀번호 암호화
        admin.setRole(adminDTO.getRole());
        return admin;
    }

    // Admin 엔티티를 AdminDTO로 변환
    public AdminDTO entityToDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getRole());
    }

    public Admin saveAdmin(AdminDTO adminDTO) {
        Admin admin = dtoToEntity(adminDTO);
        return adminRepository.save(admin);
    }

    public Admin findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return admin;
        }
        return null;
    }
}
