package dev.kopfire.site.printer.core.model;

import dev.kopfire.site.printer.db.entity.Offices;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartridgeDTO {

    private Long id;
    private TypesCartridges type_cartridge;
    private String text_qr;
    private String status;
    private Offices office;
    private String text_status;

    public CartridgeDTO(TypesCartridges type_cartridge, String text_qr, String status, String text_status, Offices office){
        this.type_cartridge = type_cartridge;
        this.text_qr = text_qr;
        this.text_status = text_status;
        this.status = status;
        this.office = office;
    }
}
