package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PrinterDTO {

    private long id;

    private String name;

    private List<String> types_cartridges;

    private int statusBase;
    private int statusRefill;
    private int statusUsers;
    private String normalTypes;

    public PrinterDTO(String name, List<String> types_cartridges) {
        this.name = name;
        this.types_cartridges = types_cartridges;
    }
}
