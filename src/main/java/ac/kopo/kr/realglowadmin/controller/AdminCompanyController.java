package ac.kopo.kr.realglowadmin.controller;

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
    private AdminService adminService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", itemService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(ItemDTO dto, RedirectAttributes redirectAttributes){
        ItemDTO itemDTO = ItemDTO.builder()
                .itemName(dto.getItemName())
                .color(dto.getColor())
                .colorName(dto.getColorName())
                .itemtypeDTO(dto.getItemtypeDTO())
                .companyDTO(dto.getCompanyDTO())
                .link(dto.getLink())
                .build();
        Integer id = itemService.register(itemDTO);
        redirectAttributes.addFlashAttribute("msg", id);
        return "redirect:/admin/Company/list";
    }
    @GetMapping({"/read", "/modify"})
    public void read(Integer id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        ItemDTO dto = itemService.get(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(ItemDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        itemService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("id", dto.getId());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:/admin/Company/read";
    }
    @PostMapping("/remove")
    public String remove(Integer id, RedirectAttributes redirectAttributes){
        itemService.remove_item(id);
        redirectAttributes.addFlashAttribute("msg",id);
        return "redirect:/admin/Company/list";
    }

}