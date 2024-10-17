package ac.kopo.kr.realglowadmin.controller;


import ac.kopo.kr.realglowadmin.dto.AdminDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.NoticeDTO;
import ac.kopo.kr.realglowadmin.dto.PageRequestDTO;
import ac.kopo.kr.realglowadmin.entity.Admin;
import ac.kopo.kr.realglowadmin.entity.Notice;
import ac.kopo.kr.realglowadmin.repository.AdminRepository;
import ac.kopo.kr.realglowadmin.repository.NoticeRepository;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Notice/")
public class NoticeController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private AdminRepository adminRepository;
    @GetMapping("/")
    public String redirect(PageRequestDTO pageRequestDTO, Model model){
        return "redirect:/Notice/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", itemService.getNoticeList(pageRequestDTO));
    }

    @GetMapping({"/read"})
    public void read(Long nno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        NoticeDTO dto = itemService.getNotice(nno);
        model.addAttribute("dto", dto);
    }



}
