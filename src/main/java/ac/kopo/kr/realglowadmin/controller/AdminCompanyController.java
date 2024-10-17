package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.CompanyDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.PageRequestDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/Company/")
public class AdminCompanyController {
    @Autowired
    private ItemService itemService;


    @GetMapping("/")
    public String redirectCompanyList() {
        return "redirect:/admin/Company/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", itemService.getCompanyList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(CompanyDTO dto, RedirectAttributes redirectAttributes){
        CompanyDTO companyDTO = CompanyDTO.builder()
                .name(dto.getName())
                .build();
        Long id = itemService.register(companyDTO);
        String msg = dto.getName()+" 회사가 등록되었습니다.";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/Company/list";
    }
    @GetMapping({"/read", "/modify"})
    public void read(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        CompanyDTO dto = itemService.getCompany(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(CompanyDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){

        itemService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("id", dto.getId());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:/admin/Company/read";
    }
    @PostMapping("/remove")
    public String remove(Long id, RedirectAttributes redirectAttributes){
        String Company_name = itemService.getCompany(id).getName();
        boolean is_remove = itemService.remove_Company(id);
        String msg = "";
        if (is_remove){
            msg = Company_name+" 회사가 삭제되었습니다.";
        }
        else{
            msg = Company_name+" 회사가 삭제되지 못했습니다. \n해당 회사로 등록되어있는 아이템을 먼저 삭제해 주세요.";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/admin/Company/list";
    }

}