package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.CompanyDTO;
import ac.kopo.kr.realglowadmin.dto.ItemDTO;
import ac.kopo.kr.realglowadmin.dto.ItemTypeDTO;
import ac.kopo.kr.realglowadmin.entity.Company;
import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.entity.ItemType;
import ac.kopo.kr.realglowadmin.repository.CompanyRepository;
import ac.kopo.kr.realglowadmin.repository.ItemRepository;
import ac.kopo.kr.realglowadmin.repository.ItemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItem(Integer id, Item updatedItem) {
        Item item = Item.builder()
                .id(id)
                .itemName(updatedItem.getItemName())
                .color(updatedItem.getColor()).
                build();
        itemRepository.save(item);
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    public Item dtoToEntity(ItemDTO itemDTO) {
        // Company와 ItemType FK 관계 처리
        Company company = companyRepository.findById(itemDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        ItemType itemType = itemTypeRepository.findById(itemDTO.getItemTypeId())
                .orElseThrow(() -> new RuntimeException("ItemType not found"));

        // Lombok 빌더 패턴을 사용한 Item 생성
        Item item = Item.builder()
                .itemName(itemDTO.getItemName())
                .color(itemDTO.getColor())
                .id(itemDTO.getId())
                .colorName(itemDTO.getColorName())
                .company(company)  // FK 설정
                .itemType(itemType) // FK 설정
                .link(itemDTO.getLink())
                .build();
        return item;
    }


    // Item 엔티티를 ItemDTO로 변환
    public ItemDTO entityToDTO(Item item) {
        return new ItemDTO(item.getId(), item.getItemName(), item.getColor(), item.getColorName(), item.getCompany().getId(), item.getItemType().getId(), item.getLink());
    }

    // 동일한 방식으로 ItemType과 Company에 대해서도 변환 로직 추가
    public ItemType dtoToEntity(ItemTypeDTO itemTypeDTO) {
        ItemType itemType = ItemType
                .builder()
                .id(itemTypeDTO.getId())
                .typeName(itemTypeDTO.getTypeName())
                .build();
        return itemType;
    }

    public ItemTypeDTO entityToDTO(ItemType itemType) {
        return new ItemTypeDTO(itemType.getId(), itemType.getTypeName());
    }

    public Company dtoToEntity(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .id(companyDTO.getId())
                .name(companyDTO.getName())
                .build();
        return company;
    }




    public CompanyDTO entityToDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }



    public Long register(ItemDTO dto) {
        Item item = Item.builder()
                .colorName(dto.getColorName())
                .itemName(dto.getItemName())
                .color(dto.getColor())
                .link(dto.getLink())
                .company(dto.getCompanyId())
                .itemType(dto.getItem_Type_name())
                .build();
        repository.save(entity);

        return entity.getGno();
    }
}
