package ac.kopo.kr.realglowadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private Integer id;
    private String itemName;
    private String color;
    private String colorName;
    private Integer companyId;
    private Integer itemTypeId;
    private String link;

    public ItemDTO() {}

    public ItemDTO(Integer id, String itemName, String color, String colorName, Integer companyId, Integer itemTypeId, String link) {
        this.id = id;
        this.itemName = itemName;
        this.color = color;
        this.colorName = colorName;
        this.companyId = companyId;
        this.itemTypeId = itemTypeId;
        this.link = link;
    }
}
