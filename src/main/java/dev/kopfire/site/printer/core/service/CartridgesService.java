package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.CartridgesMapper;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartridgesService {

    private final CartridgesRepository cartridgesRepository;

    private final CartridgesMapper cartridgesMapper;

    public CartridgesService(CartridgesRepository cartridgesRepository, CartridgesMapper cartridgesMapper) {
        this.cartridgesRepository = cartridgesRepository;
        this.cartridgesMapper = cartridgesMapper;
    }

    public CartridgeDTO addCartridge(CartridgeDTO cartridge) {

        Cartridge cartridgesNew = cartridgesMapper.map(cartridge, Cartridge.class);
        cartridgesNew = cartridgesRepository.save(cartridgesNew);
        cartridge = cartridgesMapper.map(cartridgesNew, CartridgeDTO.class);
        return cartridge;
    }

    public void changeCartridge(CartridgeDTO cartridge) {
        List<Cartridge> cartridgeList = cartridgesRepository.findByQR(cartridge.getText_qr());

        Cartridge cartridgesNew = cartridgeList.get(0);

        cartridgesNew.setStatus(cartridge.getStatus());
        cartridgesNew.setText_status(cartridge.getText_status());

        cartridgesRepository.save(cartridgesNew);
    }

    public CartridgeDTO getCartridge(String text_qr) {
        List<Cartridge> cartridgeList = cartridgesRepository.findByQR(text_qr);
        if (cartridgeList.size() == 0)
            return null;
        return cartridgesMapper.map(cartridgeList.get(0), CartridgeDTO.class);
    }
}
