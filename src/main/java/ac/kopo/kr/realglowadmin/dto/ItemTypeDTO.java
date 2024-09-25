package ac.kopo.kr.realglowadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypeDTO {

    private Integer id;
    private String typeName;

    public ItemTypeDTO() {}

    public ItemTypeDTO(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }
}
