package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.CompanyDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.ItemTypeDTO;
import ac.kopo.kr.realglowadmin.dto.NoticeDTO;
import ac.kopo.kr.realglowadmin.entity.*;
import ac.kopo.kr.realglowadmin.repository.CompanyRepository;
import ac.kopo.kr.realglowadmin.repository.ItemRepository;
import ac.kopo.kr.realglowadmin.repository.ItemTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class ItemTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void insert100notice(){
        IntStream.rangeClosed(101,200).forEach(i -> {
            Admin admin = adminService.findByUsername("realglowadmin");

            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .writer(admin.getUsername())
                    .title("test notice....."+i)
                    .content("test Content......"+i)
                    .build();
            itemService.register(noticeDTO);
        });
    }
    @Test
    public void insert100company(){
        IntStream.rangeClosed(101,200).forEach(i -> {
            CompanyDTO companyDTO = CompanyDTO.builder()
                    .name("테스트회사"+i)
                    .build();
            itemService.register(companyDTO);
        });
    }

    @Test
    public void insert100category(){
        IntStream.rangeClosed(101,200).forEach(i -> {
            ItemTypeDTO itemTypeDTO = ItemTypeDTO.builder()
                    .typeName("테스트카테고리"+i)
                    .build();
            itemService.register(itemTypeDTO);
        });
    }

    @Test
    public void insert100item(){
        List<ItemType> itemTypeList= itemTypeRepository.findAll();
        List<Company> companyList = companyRepository.findAll();

        Random random = new Random();


        IntStream.rangeClosed(101,200).forEach(i -> {
            int randomIndex1 = random.nextInt(itemTypeList.size());
            int randomIndex2 = random.nextInt(companyList.size());
            ItemType itemType = itemTypeList.get(randomIndex1);
            Company company = companyList.get(randomIndex2);
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);

            // 랜덤 헥사코드
            String hexColor = String.format("#%02X%02X%02X", red, green, blue);

            ItemDTO itemDTO = ItemDTO.builder()
                    .itemName("테스트아이템"+i)
                    .itemtypeDTO(itemService.entityToDTO(itemType))
                    .companyDTO(itemService.entityToDTO(company))
                    .color(hexColor)
                    .colorName("랜덤컬러"+i)
                    .link("www.test"+i+".com")
                    .build();
            itemService.register(itemDTO);
        });
    }


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
