package dev.kopfire.site.printer.db.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "printers")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Printer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    @Type(type = "list-array")
    private List<String> types_cartridges;
}
