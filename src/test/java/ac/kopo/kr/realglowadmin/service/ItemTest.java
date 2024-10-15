package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@SpringBootTest
public class ItemTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testGetItemsWithDetails() {

        // 페이징 요청 생성 (0번째 페이지, 페이지당 1개 아이템)
        PageRequest pageable = PageRequest.of(0, 5, Sort.by("id").descending());

        // 아이템 리스트 가져오기
        Page<Item> result = itemRepository.getItemsWithDetails(pageable);

        System.out.println("Total Items: " + result.getTotalElements());
        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Current Page Items:");
        result.getContent().forEach(item -> {
            System.out.println("Item ID: " + item.getId());
            System.out.println("Item Name: " + item.getItemName());
            System.out.println("Color: " + item.getColor());
            System.out.println("Color Name: " + item.getColorName());
            System.out.println("Company Name: " + item.getCompany().getName());
            System.out.println("Item Type Name: " + item.getItemType().getTypeName());
            System.out.println("Link: " + item.getLink());
            System.out.println("-----------------------------");
        });

    }
}
