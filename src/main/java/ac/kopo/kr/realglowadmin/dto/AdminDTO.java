package ac.kopo.kr.realglowadmin.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
