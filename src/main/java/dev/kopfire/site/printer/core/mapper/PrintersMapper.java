package dev.kopfire.site.printer.core.mapper;

import dev.kopfire.site.printer.core.model.PrinterDTO;
import dev.kopfire.site.printer.db.entity.Printer;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class PrintersMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Printer.class, PrinterDTO.class)
                .byDefault()
                .register();
    }
}
