package ac.kopo.kr.realglowadmin.controller;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("itemDTO", new ItemDTO());
        return "item/create";
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute("itemDTO") ItemDTO itemDTO) {
        Item item = itemService.dtoToEntity(itemDTO);
        itemService.saveItem(item);
        return "redirect:/item/list";
    }

    @GetMapping("/list")
    public String listItems(Model model) {
        // Item 리스트를 불러와서 화면에 전달하는 로직 추가
        return "item/list";
    }

    // 수정, 삭제 로직도 동일하게 추가 가능
}
