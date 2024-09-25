package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String viewProfile(Model model, @RequestParam String username) {
        AdminDTO adminDTO = adminService.entityToDTO(adminService.findByUsername(username));
        model.addAttribute("adminDTO", adminDTO);
        return "admin/login";
    }
}
