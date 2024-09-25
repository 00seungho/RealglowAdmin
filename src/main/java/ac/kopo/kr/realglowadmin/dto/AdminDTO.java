package ac.kopo.kr.realglowadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {

    private Integer id;
    private String username;
    private String password;
    private String role;

    public AdminDTO() {}

    public AdminDTO(Integer id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
