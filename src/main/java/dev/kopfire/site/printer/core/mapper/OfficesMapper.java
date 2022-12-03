package dev.kopfire.site.printer.core.mapper;

import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.db.entity.Offices;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OfficesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Offices.class, OfficesDTO.class)
                .byDefault()
                .register();
    }
}
