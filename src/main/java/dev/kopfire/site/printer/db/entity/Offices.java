package dev.kopfire.site.printer.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "offices")
public class Offices {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


    @ManyToOne()
    @JoinColumn(name="office", referencedColumnName = "id", insertable = false, updatable = false)
    private Housings housing;

    @OneToMany(targetEntity=Cartridge.class, mappedBy="id",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cartridge> cartridgeList = new ArrayList<>();

}
