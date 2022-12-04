package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.HousingsMapper;
import dev.kopfire.site.printer.core.mapper.OfficesMapper;
import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.db.entity.Housings;
import dev.kopfire.site.printer.db.entity.Offices;
import dev.kopfire.site.printer.db.repository.OfficesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfficesService {

    private final OfficesRepository officesRepository;

    private final OfficesMapper officesMapper;

    private final HousingsMapper housingsMapper;

    public OfficesService(OfficesRepository officesRepository,
                          OfficesMapper officesMapper,
                          HousingsMapper housingsMapper) {
        this.officesRepository = officesRepository;
        this.officesMapper = officesMapper;
        this.housingsMapper = housingsMapper;
    }

    public OfficesDTO getOffice(Long offices) {
        return officesMapper.map(officesRepository.getReferenceById(offices), OfficesDTO.class);
    }

    public void addOffice(OfficesDTO officesDTO) {
        Offices officesNew = officesMapper.map(officesDTO, Offices.class);
        officesRepository.save(officesNew);
    }

    public List<OfficesDTO> findAll() {
        return officesMapper.mapAsList(officesRepository.findAll(), OfficesDTO.class);
    }

    public boolean officeAlreadyExists(HousingsDTO housing, String name) {
        return officesRepository.findByHouseAndName(housingsMapper.map(housing, Housings.class), name) != null;
    }
}
