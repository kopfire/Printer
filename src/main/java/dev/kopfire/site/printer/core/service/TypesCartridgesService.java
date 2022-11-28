package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.springframework.stereotype.Service;

@Service
public class TypesCartridgesService {

    private final TypesCartridgesRepository typesCartridgesRepository;

    public TypesCartridgesService(TypesCartridgesRepository typesCartridgesRepository) {
        this.typesCartridgesRepository = typesCartridgesRepository;
    }

    public String getName(Long id) {
        TypesCartridges typeCartridge = typesCartridgesRepository.findByID(id);

        if (typeCartridge == null)
            return null;
        return typeCartridge.getName();
    }
}
