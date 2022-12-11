package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.PrinterDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.CartridgesService;
import dev.kopfire.site.printer.core.service.PrintersService;
import dev.kopfire.site.printer.core.service.TypesCartridgesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Map<String, Object> model = new HashMap<>();

        List<PrinterDTO> printers = printersService.findAll();

        int statusBase = 0;
        int statusRefill = 0;
        int statusUsers = 0;

        StringBuilder normalTypes = new StringBuilder();

        for (PrinterDTO printer : printers) {
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
            printer.setNormalTypes(normalTypes.substring(0, normalTypes.length() - 2));
            statusBase = 0;
            statusRefill = 0;
            statusUsers = 0;
            normalTypes.setLength(0);
        }

        model.put("printersData", printers);
        model.put("typesCartridgesData", typesCartridgesService.findAll());

        return new ModelAndView("/printers", "model", model);
    }

    @PostMapping("/addPrinter")
    public String addPrinter(@RequestParam("name") String name, @RequestParam("typeCartridge") String typeCartridge, RedirectAttributes redirectAttributes) {


        if (!name.matches("[a-zA-Z0-9- ]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/printers";
        }

        List<String> typesCartridges = List.of(typeCartridge.split(","));

        if (printersService.printerAlreadyExists(name)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Этот принтер уже добавлен");
            return "redirect:/printers";
        }

        PrinterDTO addPrinterDTO = new PrinterDTO(name, typesCartridges);

        printersService.addPrinter(addPrinterDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Принтер успешно добавлен");

        return "redirect:/printers";
    }

    @PostMapping("/deletePrinter")
    public String deletePrinter(@RequestParam("id_del") Long id_del) {

        printersService.deletePrinter(id_del);

        return "redirect:/printers";
    }
}