package dev.kopfire.site.printer.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartridgeDTO {

    private Long id;
    private TypesCartridgesDTO type_cartridge;
    private String text_qr;
    private String status;
    private OfficesDTO office;
    private String text_status;

    public CartridgeDTO(TypesCartridgesDTO type_cartridge, String text_qr, String status, String text_status, OfficesDTO office) {
        this.type_cartridge = type_cartridge;
        this.text_qr = text_qr;
        this.text_status = text_status;
        this.status = status;
        this.office = office;
    }
}
