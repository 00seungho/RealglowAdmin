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
    @Column(name = "NNO")
    private Long nno;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "CONTENT")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITERID", referencedColumnName = "ID")
    private Admin writer;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
