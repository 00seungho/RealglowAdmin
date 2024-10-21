package ac.kopo.kr.realglowadmin.service;

import ac.kopo.kr.realglowadmin.dto.*;
import ac.kopo.kr.realglowadmin.entity.*;
import ac.kopo.kr.realglowadmin.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private AdminRepository adminRepository;
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
    public Admin DTOToEntity(AdminDTO adminDTO) {
        Admin admin = Admin.builder()
                .id(adminDTO.getId())
                .password(adminDTO.getPassword())
                .username(adminDTO.getUsername())
                .build();
        return admin;
    }

    public AdminDTO entityToDTO(Admin admin){
        AdminDTO adminDTO = AdminDTO.builder()
                .id(admin.getId())
                .password(admin.getPassword())
                .username(admin.getUsername())
                .build();
        return adminDTO;
    }

    public NoticeDTO entityToDTO(Notice notice) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter().getUsername())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();
        return noticeDTO;
    }

    public Notice dtoToEntity(NoticeDTO noticeDTO) {
        Admin admin = adminRepository.findByUsername(noticeDTO.getWriter());
        Notice notice = Notice.builder()
                .nno(noticeDTO.getNno())
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .writer(admin)
                .build();
        return notice;
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

    public PageResultDTO<ItemDTO, Item> getItemList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Function<Item, ItemDTO> fn = item -> entityToDTO(item); // Item에서 DTO로 변환
        Page<Item> result = itemRepository.searchPage(pageRequestDTO.getType(),pageRequestDTO.getKeyword(),pageable);
        return new PageResultDTO<>(result, fn);
    }

    public PageResultDTO<NoticeDTO, Notice> getNoticeList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Function<Notice, NoticeDTO> fn = notice -> entityToDTO(notice); // Item에서 DTO로 변환
        // PageRequestDTO로 페이징 정보 설정, 정렬 기준은 'id' 내림차순
        Page<Notice> result = noticeRepository.searchPage(pageRequestDTO.getType(),pageRequestDTO.getKeyword(),pageable);

        // PageResultDTO를 반환
        return new PageResultDTO<>(result, fn);
    }

    public PageResultDTO<CompanyDTO, Company> getCompanyList(PageRequestDTO pageRequestDTO) {
        // Function to convert Item to ItemDTO
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        String keyword = pageRequestDTO.getKeyword();
        Page<Company> result;
        if(keyword == null || keyword.isEmpty()) {
            result = companyRepository.getCompanysWithDetails(pageable);
        }
        else{
            result = companyRepository.findByNameContaining(keyword,pageable);
        }
        Function<Company, CompanyDTO> fn = company -> entityToDTO(company); // Item에서 DTO로 변환
        return new PageResultDTO<>(result, fn);
    }

    public PageResultDTO<ItemTypeDTO, ItemType> getItemTypeList(PageRequestDTO pageRequestDTO) {
        // PageRequestDTO로 페이징 정보 설정, 정렬 기준은 'id' 내림차순
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Page<ItemType> result;
        if(keyword == null || keyword.isEmpty()) {
            result = itemTypeRepository.getItemTypesWithDetails(pageable);
        }
        else{
            result = itemTypeRepository.findByTypeNameContaining(keyword,pageable);
        }
        Function<ItemType, ItemTypeDTO> fn = itemType -> entityToDTO(itemType); // Item에서 DTO로 변환



        // PageResultDTO를 반환
        return new PageResultDTO<>(result, fn);
    }


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


    public Long register(ItemDTO dto) {
        Item item = dtoToEntity(dto);
        itemRepository.save(item);
        return item.getId();
    }

    public Long register(ItemTypeDTO dto) {
        ItemType itemType = dtoToEntity(dto);
        itemTypeRepository.save(itemType);
        return itemType.getId();
    }

    public Long register(CompanyDTO dto) {
        Company company = dtoToEntity(dto);
        companyRepository.save(company);
        return company.getId();
    }

    public Long register(NoticeDTO dto) {
        Notice notice = dtoToEntity(dto);
        noticeRepository.save(notice);
        return notice.getNno();
    }

    @Transactional
    public void modify(ItemDTO dto,Long companyId,Long itemTypeId) {
        Company company  = companyRepository.findById(companyId).get();
        ItemType itemType = itemTypeRepository.findById(itemTypeId).get();
        Item item = itemRepository.getReferenceById(dto.getId());
        item.changeItemName(dto.getItemName());
        item.changeColor(dto.getColor());
        item.changeColorName(dto.getColorName());
        item.changeLink(dto.getLink());
        item.changeItemType(itemType);
        item.changeCompany(company);
        itemRepository.save(item);
    }

    @Transactional
    public void modify(NoticeDTO dto) {
        Notice notice = noticeRepository.getReferenceById(dto.getNno());
        notice.changeTitle(dto.getTitle());
        notice.changeContent(dto.getContent());
        noticeRepository.save(notice);
    }

    @Transactional
    public void modify(CompanyDTO dto) {
        Company company = companyRepository.getReferenceById(dto.getId());
        company.changeName(dto.getName());
        companyRepository.save(company);
    }

    public void modify(ItemTypeDTO dto) {
        ItemType itemType = itemTypeRepository.getReferenceById(dto.getId());
        itemType.changeTypeName(dto.getTypeName());
        itemTypeRepository.save(itemType);
    }



    @Transactional
    public void remove_item(Long id) {
        itemRepository.deleteById(id);
    }
    @Transactional
    public void remove_notice(Long nno) {
        noticeRepository.deleteById(nno);
    }
    @Transactional
    public boolean remove_Company(Long id) {
        List<Item> items = itemRepository.findByCompanyId(id);
        if (!items.isEmpty()) {
            return false;
        }
        // 삭제 가능할 때만 Company 삭제
        companyRepository.deleteById(id);
        return true;
    }

    @Transactional
    public boolean remove_ItemType(Long id) {
        List<Item> items = itemRepository.findByItemTypeId(id);
        if (!items.isEmpty()) {
            return false;
        }
        // 삭제 가능할 때만 Company 삭제
        itemTypeRepository.deleteById(id);
        return true;
    }


    @Transactional
    public void remove_company(Long id) {
        companyRepository.deleteById(id);
    }

    public ItemDTO getItem(Long id) {
        Item item = itemRepository.getItemWithDetails(id);
        ItemDTO itemDTO = entityToDTO(item);
        return itemDTO;
    }

    public CompanyDTO getCompany(Long id) {
        Company company = companyRepository.getCompany(id);
        CompanyDTO companyDTO = entityToDTO(company);
        return companyDTO;
    }

    public ItemTypeDTO getItemType(Long id) {
        ItemType itemType = itemTypeRepository.getReferenceById(id);
        ItemTypeDTO itemtypeDTO = entityToDTO(itemType);
        return itemtypeDTO;
    }


    public NoticeDTO getNotice(Long nno) {
        Notice notice = noticeRepository.getReferenceById(nno);
        NoticeDTO noticeDTO = entityToDTO(notice);
        return noticeDTO;
    }

    public List<CompanyDTO> getAllCompanyDTO(){
        List<Company> companyList = companyRepository.findAll();
        List<CompanyDTO> companyDTOList = companyList.stream()
                .map(this::entityToDTO) // this refers to the current class containing entityToDTO method
                .collect(Collectors.toList());
        return companyDTOList;
    }

    public List<ItemTypeDTO> getAllItemTypeDTO(){
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        List<ItemTypeDTO> itemTypeDTOList = itemTypeList.stream()
                .map(this::entityToDTO) // this refers to the current class containing entityToDTO method
                .collect(Collectors.toList());
        return itemTypeDTOList;
    }



}
