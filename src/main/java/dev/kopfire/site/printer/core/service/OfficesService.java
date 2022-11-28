package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.db.repository.OfficesRepository;
import org.springframework.stereotype.Service;

@Service
public class OfficesService {

    private final OfficesRepository officesRepository;

    public OfficesService(OfficesRepository officesRepository) {
        this.officesRepository = officesRepository;
    }
}
