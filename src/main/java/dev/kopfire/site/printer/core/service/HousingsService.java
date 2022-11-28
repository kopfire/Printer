package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.HousingsRepository;
import org.springframework.stereotype.Service;

@Service
public class HousingsService {

    private final HousingsRepository housingsRepository;

    public HousingsService(HousingsRepository housingsRepository) {
        this.housingsRepository = housingsRepository;
    }

    public String getName(Long id) {
        TypesCartridges typeCartridge = housingsRepository.findByID(id);

        if (typeCartridge == null)
            return null;
        return typeCartridge.getName();
    }
}
