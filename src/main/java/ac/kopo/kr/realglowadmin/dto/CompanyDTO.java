package ac.kopo.kr.realglowadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {

    private Integer id;
    private String name;

    public CompanyDTO() {}

    public CompanyDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
