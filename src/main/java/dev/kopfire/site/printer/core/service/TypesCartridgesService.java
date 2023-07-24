package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypesCartridgesService {

    private ModelMapper modelMapper = new ModelMapper();

    private final TypesCartridgesRepository typesCartridgesRepository;

    public TypesCartridgesService(TypesCartridgesRepository typesCartridgesRepository) {
        this.typesCartridgesRepository = typesCartridgesRepository;
    }

    public List<TypesCartridgesDTO> findAll() {
        return modelMapper.map(typesCartridgesRepository.findAll(Sort.by(Sort.Direction.ASC, "name")), new TypeToken<List<TypesCartridgesDTO>>() {}.getType());
    }

    public TypesCartridgesDTO getById(long id) {
        return modelMapper.map(typesCartridgesRepository.getReferenceById(id), TypesCartridgesDTO.class);
    }

    public boolean typesCartridgesAlreadyExists(String name) {
        return typesCartridgesRepository.findByName(name) != null;
    }

    public TypesCartridgesDTO getByName(String name) {
        return modelMapper.map(typesCartridgesRepository.findByName(name), TypesCartridgesDTO.class);
    }

    public void addTypesCartridges(TypesCartridgesDTO typesCartridgesDTO) {
        TypesCartridges typesCartridgesNew = modelMapper.map(typesCartridgesDTO, TypesCartridges.class);
        typesCartridgesRepository.save(typesCartridgesNew);
    }

    public void deleteTypesCartridges(Long id) {
        typesCartridgesRepository.delete(typesCartridgesRepository.getReferenceById(id));
    }

    public void changeTypesCartridges(Long id, String name) {

        TypesCartridges typesCartridgesNew = typesCartridgesRepository.getReferenceById(id);

        typesCartridgesNew.setName(name);

        typesCartridgesRepository.save(typesCartridgesNew);
    }
}
