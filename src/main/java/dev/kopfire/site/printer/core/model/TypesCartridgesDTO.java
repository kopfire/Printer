package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TypesCartridgesDTO {

    private long id;

    private String name;

    public TypesCartridgesDTO(String name){
        this.name = name;
    }
}
