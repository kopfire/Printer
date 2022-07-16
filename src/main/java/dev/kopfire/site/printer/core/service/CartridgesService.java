package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.CartridgesMapper;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartridgesService {

    @Autowired
    private CartridgesRepository cartridgesRepository;

    @Autowired
    private CartridgesMapper cartridgesMapper;

    public CartridgeDTO addCartidge(CartridgeDTO cartridge){

        Cartridge cartridgesNew = cartridgesMapper.map(cartridge, Cartridge.class);
        cartridgesNew = cartridgesRepository.save(cartridgesNew);
        cartridge = cartridgesMapper.map(cartridgesNew, CartridgeDTO.class);
        return cartridge;
    }

    public CartridgeDTO getCartidge(String text_qr){
        List<Cartridge> cartridgeList = cartridgesRepository.findByQR(text_qr);
        if (cartridgeList.size() == 0)
            return null;
        return cartridgesMapper.map(cartridgeList.get(0), CartridgeDTO.class);
    }
}
