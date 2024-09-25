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
        Item item = getItemById(id);
        item.setItemName(updatedItem.getItemName());
        item.setColor(updatedItem.getColor());
        itemRepository.save(item);
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    public Item dtoToEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setItemName(itemDTO.getItemName());
        item.setColor(itemDTO.getColor());
        item.setColorName(itemDTO.getColorName());

        // Company와 ItemType FK 관계 처리
        Company company = companyRepository.findById(itemDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        ItemType itemType = itemTypeRepository.findById(itemDTO.getItemTypeId())
                .orElseThrow(() -> new RuntimeException("ItemType not found"));

        item.setCompany(company); // FK 설정
        item.setItemType(itemType); // FK 설정

        item.setLink(itemDTO.getLink());
        return item;
    }

    // Item 엔티티를 ItemDTO로 변환
    public ItemDTO entityToDTO(Item item) {
        return new ItemDTO(item.getId(), item.getItemName(), item.getColor(), item.getColorName(), item.getCompany().getId(), item.getItemType().getId(), item.getLink());
    }

    // 동일한 방식으로 ItemType과 Company에 대해서도 변환 로직 추가
    public ItemType dtoToEntity(ItemTypeDTO itemTypeDTO) {
        ItemType itemType = new ItemType();
        itemType.setId(itemTypeDTO.getId());
        itemType.setTypeName(itemTypeDTO.getTypeName());
        return itemType;
    }

    public ItemTypeDTO entityToDTO(ItemType itemType) {
        return new ItemTypeDTO(itemType.getId(), itemType.getTypeName());
    }

    public Company dtoToEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        return company;
    }

    public CompanyDTO entityToDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }
}
