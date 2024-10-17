package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.NoticeDTO;
import ac.kopo.kr.realglowadmin.dto.PageRequestDTO;
import ac.kopo.kr.realglowadmin.entity.Admin;
import ac.kopo.kr.realglowadmin.entity.Notice;
import ac.kopo.kr.realglowadmin.repository.AdminRepository;
import ac.kopo.kr.realglowadmin.service.AdminService;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/Annoumcement/")
public class AnnoumcementController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/")
    public String redirectItemList() {
        return "redirect:/Notice/list";
    }

    @GetMapping("/register")
    public void register(){
    }

    @PostMapping("/register")
    public String registerPost(NoticeDTO dto, RedirectAttributes redirectAttributes){
        // 현재 로그인한 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 사용자 이름 가져오기

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(username)
                .build();
        Long nno = itemService.register(noticeDTO);
        String msg = nno+"번 글이 등록되었습니다.";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/Notice/list"; // 등록 후 리다이렉트할 페이지
    }

    @GetMapping({"/modify"})
    public void read(Long nno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        NoticeDTO dto = itemService.getNotice(nno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        itemService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("nno", dto.getNno());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:/Notice/read";
    }
    @PostMapping("/remove")
    public String remove(Long nno, RedirectAttributes redirectAttributes){
        itemService.remove_notice(nno);
        String msg = nno+"번 글이 삭제되었습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/Notice/list";
    }



}