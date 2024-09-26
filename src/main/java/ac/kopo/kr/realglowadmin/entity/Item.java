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
}