package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.ItemTypeDTO;
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
@RequestMapping("/admin/ItemType/")
public class AdminItemTypeController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String redirectItemTypeList() {
        return "redirect:/admin/ItemType/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", itemService.getItemTypeList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(ItemTypeDTO dto, RedirectAttributes redirectAttributes){
        ItemTypeDTO itemTypeDTO = ItemTypeDTO.builder()
                .typeName(dto.getTypeName())
                .build();
        Long id = itemService.register(itemTypeDTO);

        String msg = dto.getTypeName()+" 카테고리가 등록되었습니다.";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/ItemType/list";
    }
    @GetMapping({"/read", "/modify"})
    public void read(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        ItemTypeDTO dto = itemService.getItemType(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(ItemTypeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        itemService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("id", dto.getId());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:/admin/ItemType/read";
    }
    @PostMapping("/remove")
    public String remove(Long id, RedirectAttributes redirectAttributes){
        String itemTypeName = itemService.getItemType(id).getTypeName();
        itemService.remove_ItemType(id);
        boolean is_remove = itemService.remove_Company(id);

        String msg = "";
        if (is_remove){
            msg = itemTypeName+" 카테고리가 삭제되었습니다.";
        }
        else{
            msg = itemTypeName+" 카테고리가 삭제되지 못했습니다. \n해당 카테고리로 등록되어있는 아이템을 먼저 삭제해 주세요.";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/admin/ItemType/list";
    }



    }
