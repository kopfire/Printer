package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfficesDTO {

    private Long id;

    private String name;

    private HousingsDTO housing;
}
