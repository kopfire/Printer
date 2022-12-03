package dev.kopfire.site.printer.core.mapper;

import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.db.entity.Housings;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class HousingsMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Housings.class, HousingsDTO.class)
                .byDefault()
                .register();
    }
}
