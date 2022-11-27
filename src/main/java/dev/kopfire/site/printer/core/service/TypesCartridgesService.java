package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.CartridgesMapper;
import dev.kopfire.site.printer.core.mapper.TypesCartridgesMapper;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypesCartridgesService {

    private final TypesCartridgesRepository typesCartridgesRepository;

    private final TypesCartridgesMapper typesCartridgesMapper;

    public TypesCartridgesService(TypesCartridgesRepository typesCartridgesRepository, TypesCartridgesMapper typesCartridgesMapper) {
        this.typesCartridgesRepository = typesCartridgesRepository;
        this.typesCartridgesMapper = typesCartridgesMapper;
    }

    public String getName(Long id) {
        TypesCartridges typeCartridge = typesCartridgesRepository.findByID(id);

        if (typeCartridge == null)
            return null;
        return typeCartridge.getName();
    }
}
