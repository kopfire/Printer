package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfficesDTO {

    private long id;

    private String name;

    private HousingsDTO housing;

    public OfficesDTO(String name, HousingsDTO housing) {
        this.name = name;
        this.housing = housing;
    }
}
