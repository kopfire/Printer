package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.PrinterDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.CartridgesService;
import dev.kopfire.site.printer.core.service.PrintersService;
import dev.kopfire.site.printer.core.service.TypesCartridgesService;
import dev.kopfire.site.printer.db.entity.Cartridge;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrintersController {

    private final PrintersService printersService;

    private final CartridgesService cartridgesService;

    private final TypesCartridgesService typesCartridgesService;


    public PrintersController(PrintersService printersService,
                              CartridgesService cartridgesService,
                              TypesCartridgesService typesCartridgesService) {
        this.printersService = printersService;
        this.cartridgesService = cartridgesService;
        this.typesCartridgesService = typesCartridgesService;
    }

    @GetMapping("/printers")
    public ModelAndView printersPage() {
        List<PrinterDTO> printers = printersService.findAll();

        int statusBase = 0;
        int statusRefill = 0;
        int statusUsers = 0;

        StringBuilder normalTypes = new StringBuilder();

        for (PrinterDTO printer : printers){
            for (String type : printer.getTypes_cartridges()) {
                normalTypes.append(type.toUpperCase()).append(", ");
                TypesCartridgesDTO typesCartridgesDTO = typesCartridgesService.getByName(type);
                for (CartridgeDTO cartridge : cartridgesService.findByTypeCartridges(typesCartridgesDTO)) {
                    switch (cartridge.getStatus()) {
                        case "В отделе" -> statusBase++;
                        case "На заправке" -> statusRefill++;
                        case "У пользователей" -> statusUsers++;
                    }
                }
            }
            printer.setStatusBase(statusBase);
            printer.setStatusRefill(statusRefill);
            printer.setStatusUsers(statusUsers);
            printer.setNormalTypes(normalTypes.substring(0, normalTypes.length()-2));
            statusBase = 0;
            statusRefill = 0;
            statusUsers = 0;
            normalTypes.setLength(0);
        }

        return new ModelAndView("/printers", "printersData", printers);
    }
}