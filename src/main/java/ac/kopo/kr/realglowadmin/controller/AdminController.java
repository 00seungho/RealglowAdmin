package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.PageRequestDTO;
import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.service.AdminService;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private ItemService itemService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "/admin/item/item-create";
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute("itemDTO") ItemDTO itemDTO) {
        Item item = itemService.dtoToEntity(itemDTO);
        itemService.saveItem(item);
        return "redirect:/admin/item/management";
    }

    @GetMapping("/management")
    public String listItems(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", itemService.getList(pageRequestDTO));
        return "/admin/item/item-management";
    }



    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("adminDTO", new AdminDTO()); // 빈 AdminDTO를 모델에 추가
        return "/admin/register"; // 회원가입 페이지
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO); // Admin 저장
        return "redirect:/admin/login"; // 등록 후 로그인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String viewProfile() {
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            // 사용자 이름과 비밀번호로 인증 시도
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증이 성공하면 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 성공 후 관리 페이지로 리다이렉트
            System.out.println("성공");
            return "redirect:/admin/management";

        } catch (Exception e) {
            // 인증 실패 시 에러 메시지와 함께 다시 로그인 페이지로
            String msg = "Invalid username or password.";
            model.addAttribute("loginError", msg);  // 에러 메시지 설정
            return "/admin/login";
        }




    }
}