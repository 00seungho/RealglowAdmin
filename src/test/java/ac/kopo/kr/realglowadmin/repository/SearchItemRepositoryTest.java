package ac.kopo.kr.realglowadmin.repository;

import ac.kopo.kr.realglowadmin.dto.PageRequestDTO;
import ac.kopo.kr.realglowadmin.entity.Company;
import ac.kopo.kr.realglowadmin.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class SearchItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSearchItemRepository() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        itemRepository.searchPage("t","테스트",pageable);
    }

    @Test
    public void testSearchRepository() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        PageRequestDTO pageRequestDTO = PageRequestDTO
                .builder()
                .keyword("테스트")
                .build();
        itemService.getCompanyList(pageRequestDTO);
    }

    @Test
    public void testSearchCompanyRepository() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        String keyword = "테스트회사20";

        Page<Company> compayPage = companyRepository.findByNameContaining(keyword,pageable);
        System.out.println("현재 페이지 번호: " + compayPage.getNumber());
        System.out.println("전체 페이지 수: " + compayPage.getTotalPages());
        System.out.println("전체 결과 수: " + compayPage.getTotalElements());

        // for문으로 페이지 내용 출력
        for (Company company : compayPage.getContent()) {
            System.out.println("회사 이름: " + company.getName());
            System.out.println("회사 id: " + company.getId());
            // 다른 필요한 필드 출력
        }


    }
    @Test
    public void testSearchCompanyService() {
        PageRequestDTO pageRequestDTO = PageRequestDTO
                .builder().build();
        pageRequestDTO.setKeyword("테스트회사20");
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        String keyword = pageRequestDTO.getType();

        Page<Company> compayPage = companyRepository.findByNameContaining(keyword,pageable);
        System.out.println("현재 페이지 번호: " + compayPage.getNumber());
        System.out.println("전체 페이지 수: " + compayPage.getTotalPages());
        System.out.println("전체 결과 수: " + compayPage.getTotalElements());

        // for문으로 페이지 내용 출력
        for (Company company : compayPage.getContent()) {
            System.out.println("회사 이름: " + company.getName());
            System.out.println("회사 id: " + company.getId());
            // 다른 필요한 필드 출력
        }


    }
    }

