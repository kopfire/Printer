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
@Table(name = "types_cartridges")
public class TypesCartridges {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(targetEntity = Cartridge.class, mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cartridge> cartridgeList = new ArrayList<>();
}
