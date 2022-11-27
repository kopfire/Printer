package dev.kopfire.site.printer.core.mapper;

import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class TypesCartridgesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(TypesCartridges.class, TypesCartridgesDTO.class)
                .byDefault()
                .register();
    }
}
