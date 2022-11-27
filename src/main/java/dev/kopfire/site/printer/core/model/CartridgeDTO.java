package dev.kopfire.site.printer.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartridgeDTO {

    private Long id;
    private Long type_cartridge;
    private String text_qr;
    private String status;
    private String text_status;

    public CartridgeDTO(Long type_cartridge, String text_qr, String status, String text_status){
        this.type_cartridge = type_cartridge;
        this.text_qr = text_qr;
        this.text_status = text_status;
        this.status = status;
    }
}
