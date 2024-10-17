package ac.kopo.kr.realglowadmin.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "notice")
@ToString(exclude = "writer")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin writer;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
