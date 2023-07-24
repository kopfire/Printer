package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.db.entity.Housings;
import dev.kopfire.site.printer.db.entity.Offices;
import dev.kopfire.site.printer.db.repository.OfficesRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfficesService {

    private ModelMapper modelMapper = new ModelMapper();

    private final OfficesRepository officesRepository;

    public OfficesService(OfficesRepository officesRepository) {
        this.officesRepository = officesRepository;
    }

    public OfficesDTO getOffice(Long offices) {
        return modelMapper.map(officesRepository.getReferenceById(offices), OfficesDTO.class);
    }

    public void addOffice(OfficesDTO officesDTO) {
        Offices officesNew = modelMapper.map(officesDTO, Offices.class);
        officesRepository.save(officesNew);
    }

    public List<OfficesDTO> findAll() {
        return modelMapper.map(officesRepository.findAll(Sort.by(Sort.Direction.ASC, "housing").and(Sort.by(Sort.Direction.ASC, "name"))), new TypeToken<List<OfficesDTO>>() {}.getType());
    }

    public boolean officeAlreadyExists(HousingsDTO housing, String name) {
        return officesRepository.findByHouseAndName(modelMapper.map(housing, Housings.class), name) != null;
    }

    public void deleteOffice(Long id) {
        officesRepository.delete(officesRepository.getReferenceById(id));
    }

    public void changeOffice(Long id, String name, HousingsDTO housing) {

        Offices offices = officesRepository.getReferenceById(id);

        offices.setName(name);

        offices.setHousing(modelMapper.map(housing, Housings.class));

        officesRepository.save(offices);
    }
}
