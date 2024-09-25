package ac.kopo.kr.realglowadmin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "itemtype", uniqueConstraints = {@UniqueConstraint(columnNames = "TYPE_NAME")})
@Getter
@Setter
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE_NAME", nullable = false)
    private String typeName;
}
