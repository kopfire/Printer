package dev.kopfire.site.printer.core.mapper;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CartridgesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Cartridge.class, CartridgeDTO.class)
                .byDefault()
                .register();
    }
}
