package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.service.AdminService;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Auth")
public class AuthController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String redirectToLogin() {
        // "/" 경로로 들어오면 "/login" 페이지로 리다이렉트
        return "redirect:/Auth/login";
    }

    @GetMapping("/login")
    public void viewProfile() {
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
            return "redirect:/admin/ItemList/list";

        } catch (Exception e) {
            // 인증 실패 시 에러 메시지와 함께 다시 로그인 페이지로
            String msg = "Invalid username or password.";
            model.addAttribute("loginError", msg);  // 에러 메시지 설정
            return "redirect:/Auth/login";
        }




    }
}
