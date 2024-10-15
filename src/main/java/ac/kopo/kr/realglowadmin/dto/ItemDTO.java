package ac.kopo.kr.realglowadmin.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Integer id;
    private String itemName;
    private String color;
    private String colorName;
    private ItemTypeDTO itemtypeDTO;
    private CompanyDTO companyDTO;
    private String link;
}


