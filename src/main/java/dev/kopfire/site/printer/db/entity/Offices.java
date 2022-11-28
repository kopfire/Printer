package dev.kopfire.site.printer.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "housing", referencedColumnName = "id")
    private Housings housing;

    @OneToOne(mappedBy = "types_cartridges")
    private Cartridge cartridge;
}
