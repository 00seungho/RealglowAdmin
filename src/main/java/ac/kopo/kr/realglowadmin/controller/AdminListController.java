package ac.kopo.kr.realglowadmin.controller;

import ac.kopo.kr.realglowadmin.dto.*;

import ac.kopo.kr.realglowadmin.entity.Company;
import ac.kopo.kr.realglowadmin.entity.ItemType;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/ItemList/")
public class AdminListController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String redirectItemList() {
        return "redirect:/admin/ItemList/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", itemService.getItemList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(Model model){
        List<ItemTypeDTO> itemTypeDTOList = itemService.getAllItemTypeDTO();
        List<CompanyDTO> companyDTOList = itemService.getAllCompanyDTO();
        model.addAttribute("itemTypeDTOList", itemTypeDTOList);
        model.addAttribute("companyDTOList", companyDTOList);
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute ItemDTO dto,
                               @RequestParam Long companyId,
                               @RequestParam Long itemTypeId,
                               RedirectAttributes redirectAttributes){
        CompanyDTO companyDTO = itemService.getCompany(companyId);
        ItemTypeDTO itemTypeDTO = itemService.getItemType(itemTypeId);
        ItemDTO itemDTO = ItemDTO.builder()
                .itemName(dto.getItemName())
                .color(dto.getColor())
                .colorName(dto.getColorName())
                .itemtypeDTO(itemTypeDTO)
                .companyDTO(companyDTO)
                .link(dto.getLink())
                .build();
        Long id = itemService.register(itemDTO);
        String itemName = itemDTO.getItemName();
        String msg = itemName + " 아이템이 등록되었습니다.";
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/admin/ItemList/list";
    }
    @GetMapping("/read")
    public void read(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        ItemDTO dto = itemService.getItem(id);
        model.addAttribute("dto", dto);
    }

    @GetMapping( "/modify")
    public void modify(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        List<ItemTypeDTO> itemTypeDTOList = itemService.getAllItemTypeDTO();
        List<CompanyDTO> companyDTOList = itemService.getAllCompanyDTO();
        ItemDTO dto = itemService.getItem(id);
        model.addAttribute("dto", dto);
        model.addAttribute("itemTypeDTOList", itemTypeDTOList);
        model.addAttribute("companyDTOList", companyDTOList);
    }


    @PostMapping("/modify")
    public String modify(@ModelAttribute ItemDTO dto,
                         @RequestParam Long companyId,
                         @RequestParam Long itemTypeId
            , @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        itemService.modify(dto,companyId,itemTypeId);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("id", dto.getId());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        return "redirect:/admin/ItemList/read";
    }
    @PostMapping("/remove")
    public String remove(Long id, RedirectAttributes redirectAttributes){
        String itemName = itemService.getItem(id).getItemName();
        String msg = itemName+ " 아이템이 삭제 되었습니다.";
        itemService.remove_item(id);
        redirectAttributes.addFlashAttribute("msg",id);
        return "redirect:/admin/ItemList/list";
    }



}