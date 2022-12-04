package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.HousingsMapper;
import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.db.repository.HousingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HousingsService {

    private final HousingsRepository housingsRepository;

    private final HousingsMapper housingsMapper;

    public HousingsService(HousingsRepository housingsRepository,
                           HousingsMapper housingsMapper) {
        this.housingsRepository = housingsRepository;
        this.housingsMapper = housingsMapper;
    }

    public HousingsDTO getHousingDTO(Long id) {

        return housingsMapper.map(housingsRepository.findByID(id), HousingsDTO.class);
    }

    public List<HousingsDTO> findAll() {
        return housingsMapper.mapAsList(housingsRepository.findAll(), HousingsDTO.class);
    }
}
