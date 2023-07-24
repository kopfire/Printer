package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.db.repository.HousingsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HousingsService {

    private ModelMapper modelMapper = new ModelMapper();

    private final HousingsRepository housingsRepository;

    public HousingsService(HousingsRepository housingsRepository) {
        this.housingsRepository = housingsRepository;
    }

    public HousingsDTO getHousingDTO(Long id) {

        return modelMapper.map(housingsRepository.findByID(id), HousingsDTO.class);
    }

    public List<HousingsDTO> findAll() {
        return modelMapper.map(housingsRepository.findAll(), new TypeToken<List<HousingsDTO>>() {}.getType());
    }
}
