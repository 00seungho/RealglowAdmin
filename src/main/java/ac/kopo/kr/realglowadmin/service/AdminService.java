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
        Admin admin = Admin.builder()
                .id(adminDTO.getId())
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .role(adminDTO.getRole())
                .build();
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
        return adminRepository.findByUsername(username);
    }
}
