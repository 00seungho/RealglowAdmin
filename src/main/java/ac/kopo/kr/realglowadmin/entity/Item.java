package ac.kopo.kr.realglowadmin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ITEMNAME")
    private String itemName;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "COLORNAME")
    private String colorName;

    @ManyToOne
    @JoinColumn(name = "COMPANYID", referencedColumnName = "ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "ITEMTYPEID", referencedColumnName = "ID")
    private ItemType itemType;

    @Column(name = "LINK")
    private String link;

    //    변경된 아이템이름 수정
    public void changeItemName(String ItemName){
        this.itemName = ItemName;
    }

    //    변경된 색상이름으로 수정
    public void changeColorName(String colorName){
        this.colorName = colorName;
    }

    //    변경된 색상으로 수정
    public void changeColor(String color){
        this.color = color;
    }

    public void changeLink(String link){
        this.link = link;
    }

}

