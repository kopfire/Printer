package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.mapper.TypesCartridgesMapper;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypesCartridgesService {

    private final TypesCartridgesRepository typesCartridgesRepository;

    private final TypesCartridgesMapper typesCartridgesMapper;

    public TypesCartridgesService(TypesCartridgesRepository typesCartridgesRepository,
                                  TypesCartridgesMapper typesCartridgesMapper) {
        this.typesCartridgesRepository = typesCartridgesRepository;
        this.typesCartridgesMapper = typesCartridgesMapper;
    }

    public List<TypesCartridgesDTO> findAll() {
        return typesCartridgesMapper.mapAsList(typesCartridgesRepository.findAll(), TypesCartridgesDTO.class);
    }

    public TypesCartridgesDTO getById(long id) {
        return typesCartridgesMapper.map(typesCartridgesRepository.getReferenceById(id), TypesCartridgesDTO.class);
    }

    public boolean typesCartridgesAlreadyExists(String name) {
        return typesCartridgesRepository.findByName(name) != null;
    }

    public void addTypesCartridges(TypesCartridgesDTO typesCartridgesDTO) {
        TypesCartridges typesCartridgesNew = typesCartridgesMapper.map(typesCartridgesDTO, TypesCartridges.class);
        typesCartridgesRepository.save(typesCartridgesNew);
    }
}
