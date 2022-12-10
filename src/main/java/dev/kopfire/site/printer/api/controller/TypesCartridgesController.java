package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.TypesCartridgesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class TypesCartridgesController {

    private final TypesCartridgesService typesCartridgesService;

    public TypesCartridgesController(TypesCartridgesService typesCartridgesService) {
        this.typesCartridgesService = typesCartridgesService;
    }

    @GetMapping("/types_cartridges")
    public ModelAndView cartridgesPage() {
        return new ModelAndView("/types_cartridges", Collections.singletonMap("typesCartridgesData", typesCartridgesService.findAll()));
    }

    @PostMapping("/addTypeCartridge")
    public String addCartridge(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {


        if (!name.matches("[a-zA-Z0-9-]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/types_cartridges";
        }

        if (typesCartridgesService.typesCartridgesAlreadyExists(name)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Этот тип уже добавлен");
            return "redirect:/types_cartridges";
        }

        TypesCartridgesDTO typesCartridgesDTO = new TypesCartridgesDTO(name);

        typesCartridgesService.addTypesCartridges(typesCartridgesDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Тип картриджа успешно добавлен");

        return "redirect:/types_cartridges";
    }

    @PostMapping("/deleteTypeCartridge")
    public String deleteCartridge(@RequestParam("id_del") Long id_del) {

        typesCartridgesService.deleteTypesCartridges(id_del);

        return "redirect:/types_cartridges";
    }

    @PostMapping("/changeTypeCartridge")
    public String changeCartridge(@RequestParam("id_change") Long id_change, @RequestParam("name_change") String name_change, RedirectAttributes redirectAttributes) {

        if (!name_change.matches("[a-zA-Z0-9-]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/types_cartridges";
        }

        if (typesCartridgesService.typesCartridgesAlreadyExists(name_change)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Этот тип уже добавлен");
            return "redirect:/types_cartridges";
        }

        typesCartridgesService.changeTypesCartridges(id_change, name_change);

        return "redirect:/types_cartridges";
    }
}