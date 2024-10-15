package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.*;
import ac.kopo.kr.realglowadmin.entity.Company;
import ac.kopo.kr.realglowadmin.entity.Item;
import ac.kopo.kr.realglowadmin.entity.ItemType;
import ac.kopo.kr.realglowadmin.repository.CompanyRepository;
import ac.kopo.kr.realglowadmin.repository.ItemRepository;
import ac.kopo.kr.realglowadmin.repository.ItemTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;

    public Item dtoToEntity(ItemDTO itemDTO) {
        Item item = Item.builder()
                .itemName(itemDTO.getItemName())
                .color(itemDTO.getColor())
                .id(itemDTO.getId())
                .colorName(itemDTO.getColorName())
                .company(dtoToEntity(itemDTO.getCompanyDTO()))  // FK 설정
                .itemType(dtoToEntity(itemDTO.getItemtypeDTO())) // FK 설정
                .link(itemDTO.getLink())
                .build();
        return item;
    }


    // Item 엔티티를 ItemDTO로 변환
    public ItemDTO entityToDTO(Item item) {
        ItemDTO itemDTO = ItemDTO.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .itemtypeDTO(entityToDTO(item.getItemType()))
                .companyDTO(entityToDTO(item.getCompany()))
                .link(item.getLink())
                .color(item.getColor())
                .colorName(item.getColorName())
                .build();
        return itemDTO;
    }

    public PageResultDTO<ItemDTO, Item> getList(PageRequestDTO pageRequestDTO) {
        // Function to convert Item to ItemDTO
        Function<Item, ItemDTO> fn = item -> entityToDTO(item); // Item에서 DTO로 변환

        // PageRequestDTO로 페이징 정보 설정, 정렬 기준은 'id' 내림차순
        Page<Item> result = itemRepository.getItemsWithDetails(pageRequestDTO.getPageable(Sort.by("id").descending()));

        // PageResultDTO를 반환
        return new PageResultDTO<>(result, fn);
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
        ItemTypeDTO itemTypeDTO = ItemTypeDTO.builder()
                .id(itemType.getId())
                .typeName(itemType.getTypeName())
                .build();
        return itemTypeDTO;
    }

    public Company dtoToEntity(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .id(companyDTO.getId())
                .name(companyDTO.getName())
                .build();
        return company;
    }


    public CompanyDTO entityToDTO(Company company) {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .build();
        return companyDTO;
    }


    public Integer register(ItemDTO dto) {
        Item item = dtoToEntity(dto);
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public void modify(ItemDTO dto) {
        Item item = itemRepository.getReferenceById(dto.getId());
        item.changeItemName(dto.getItemName());
        item.changeColor(dto.getColorName());
        item.changeColorName(dto.getColorName());
        item.changeLink(dto.getLink());
        itemRepository.save(item);
    }

    @Transactional
    public void remove_item(Integer id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void remove_company(Integer id) {
        companyRepository.deleteById(id);
    }

    public ItemDTO get(Integer id) {
        Item item = itemRepository.getItemWithDetails(id);
        ItemDTO itemDTO = entityToDTO(item);
        return itemDTO;
    }

    public CompanyDTO getCompany(Integer id) {
        Company company = companyRepository.getReferenceById(id);
        CompanyDTO companyDTO = entityToDTO(company);
        return companyDTO;
    }

    public ItemTypeDTO getItemType(Integer id) {
        ItemType itemType = itemTypeRepository.getReferenceById(id);
        ItemTypeDTO itemTypeDTO = entityToDTO(itemType);
        return itemTypeDTO;
    }



}
