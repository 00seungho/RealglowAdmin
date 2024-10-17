package ac.kopo.kr.realglowadmin.controller;


import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.entity.Admin;
import ac.kopo.kr.realglowadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class adminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/")
    public String redirect() {
        return "redirect:/admin/ItemList/list";
    }
    @GetMapping("/signup")
    public void signup(){
    }
    @PostMapping("/signup")
    public String signupPost(AdminDTO dto){
        String role = "ADMIN";
        AdminDTO adminDTO = AdminDTO.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(role)
                .build();
        Admin savedAdmin = adminService.saveAdmin(adminDTO);
        return "redirect:/admin/";
    }

}
