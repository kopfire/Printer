package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.CartridgesMapper;
import dev.kopfire.site.printer.core.mapper.OfficesMapper;
import dev.kopfire.site.printer.core.mapper.TypesCartridgesMapper;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.entity.Offices;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartridgesService {

    private final CartridgesRepository cartridgesRepository;

    private final CartridgesMapper cartridgesMapper;

    private final OfficesMapper officesMapper;

    private final TypesCartridgesMapper typesCartridgesMapper;

    public CartridgesService(CartridgesRepository cartridgesRepository,
                             CartridgesMapper cartridgesMapper,
                             OfficesMapper officesMapper,
                             TypesCartridgesMapper typesCartridgesMapper) {
        this.cartridgesRepository = cartridgesRepository;
        this.cartridgesMapper = cartridgesMapper;
        this.officesMapper = officesMapper;
        this.typesCartridgesMapper = typesCartridgesMapper;
    }

    public void addCartridge(CartridgeDTO cartridge) {

        Cartridge cartridgesNew = cartridgesMapper.map(cartridge, Cartridge.class);
        cartridgesRepository.save(cartridgesNew);
    }

    public void changeCartridge(CartridgeDTO cartridge) {
        Cartridge cartridgesOld = cartridgesRepository.findByQR(cartridge.getText_qr()).get(0);

        cartridgesOld.setStatus(cartridge.getStatus());
        cartridgesOld.setText_status(cartridge.getText_status());
        cartridgesOld.setOffice(officesMapper.map(cartridge.getOffice(), Offices.class));

        cartridgesRepository.save(cartridgesOld);
    }

    public void changeCartridgeFull(CartridgeDTO cartridge, Long id) {
        Cartridge cartridgesOld = cartridgesRepository.findByID(id);

        cartridgesOld.setText_qr(cartridge.getText_qr());
        cartridgesOld.setType_cartridge(typesCartridgesMapper.map(cartridge.getType_cartridge(), TypesCartridges.class));
        cartridgesOld.setStatus(cartridge.getStatus());
        cartridgesOld.setText_status(cartridge.getText_status());
        cartridgesOld.setOffice(officesMapper.map(cartridge.getOffice(), Offices.class));

        cartridgesRepository.save(cartridgesOld);
    }

    public CartridgeDTO getCartridge(String text_qr) {
        List<Cartridge> cartridgeList = cartridgesRepository.findByQR(text_qr);
        if (cartridgeList.size() == 0)
            return null;
        return cartridgesMapper.map(cartridgeList.get(0), CartridgeDTO.class);
    }

    public List<CartridgeDTO> findAll() {
        return cartridgesMapper.mapAsList(cartridgesRepository.findAll(Sort.by(Sort.Direction.ASC, "id")), CartridgeDTO.class);
    }

    public void deleteCartridge(Long id) {
        cartridgesRepository.delete(cartridgesRepository.getReferenceById(id));
    }

    public List<CartridgeDTO> findByTypeCartridges(TypesCartridgesDTO typesCartridgesDTO) {
        return cartridgesMapper.mapAsList(cartridgesRepository.findByTypeCartridge(typesCartridgesMapper.map(typesCartridgesDTO, TypesCartridges.class)), CartridgeDTO.class);
    }
}
