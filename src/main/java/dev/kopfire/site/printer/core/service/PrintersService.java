package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.PrintersMapper;
import dev.kopfire.site.printer.core.mapper.TypesCartridgesMapper;
import dev.kopfire.site.printer.core.model.PrinterDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.Printer;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.PrintersRepository;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrintersService {

    private final PrintersRepository printersRepository;

    private final PrintersMapper printersMapper;

    public PrintersService(PrintersRepository printersRepository,
                           PrintersMapper printersMapper) {
        this.printersRepository = printersRepository;
        this.printersMapper = printersMapper;
    }

    public List<PrinterDTO> findAll() {
        return printersMapper.mapAsList(printersRepository.findAll(Sort.by(Sort.Direction.ASC, "name")), PrinterDTO.class);
    }

    public PrinterDTO getById(long id) {
        return printersMapper.map(printersRepository.getReferenceById(id), PrinterDTO.class);
    }

    public boolean printerAlreadyExists(String name) {
        return printersRepository.findByName(name) != null;
    }

    public void addPrinter(PrinterDTO printerDTO) {
        Printer printer = printersMapper.map(printerDTO, Printer.class);
        printersRepository.save(printer);
    }

    public void deletePrinter(Long id) {
        printersRepository.delete(printersRepository.getReferenceById(id));
    }

    public void changePrinter(Long id, String name) {

        Printer printer = printersRepository.getReferenceById(id);
        printer.setName(name);

        printersRepository.save(printer);
    }
}
