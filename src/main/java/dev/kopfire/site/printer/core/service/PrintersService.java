package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.PrinterDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.Printer;
import dev.kopfire.site.printer.db.repository.PrintersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrintersService {

    private ModelMapper modelMapper = new ModelMapper();

    private final PrintersRepository printersRepository;

    public PrintersService(PrintersRepository printersRepository) {
        this.printersRepository = printersRepository;
    }

    public List<PrinterDTO> findAll() {
        return modelMapper.map(printersRepository.findAll(Sort.by(Sort.Direction.ASC, "name")), new TypeToken<List<PrinterDTO>>() {}.getType());
    }

    public PrinterDTO getById(long id) {
        return modelMapper.map(printersRepository.getReferenceById(id), PrinterDTO.class);
    }

    public boolean printerAlreadyExists(String name) {
        return printersRepository.findByName(name) != null;
    }

    public void addPrinter(PrinterDTO printerDTO) {
        Printer printer = modelMapper.map(printerDTO, Printer.class);
        printersRepository.save(printer);
    }

    public void deletePrinter(Long id) {
        printersRepository.delete(printersRepository.getReferenceById(id));
    }
}
