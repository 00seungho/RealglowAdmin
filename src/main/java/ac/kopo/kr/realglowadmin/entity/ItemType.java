package ac.kopo.kr.realglowadmin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "itemtype")
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE_NAME", nullable = false,unique = true)
    private String typeName;

    //아이템 타입 이름 변경
    public void changeTypeName(String typeName){
        this.typeName = typeName;
    }

}
