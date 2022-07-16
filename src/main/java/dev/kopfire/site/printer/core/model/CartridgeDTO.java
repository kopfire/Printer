package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartridgeDTO {

    private Long id;
    private Integer type_cartridge;
    private String text_qr;
    private String status;
    private String text_status;
}
