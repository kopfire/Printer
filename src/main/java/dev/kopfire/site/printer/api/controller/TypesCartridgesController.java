package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class TypesCartridgesController {

    private final TypesCartridgesRepository typesCartridgesRepository;

    public TypesCartridgesController(TypesCartridgesRepository typesCartridgesRepository) {
        this.typesCartridgesRepository = typesCartridgesRepository;
    }

    @GetMapping("/types_cartridges")
    public ModelAndView cartridgesPage() {
        return new ModelAndView("/types_cartridges", Collections.singletonMap("typesCartridgesData", typesCartridgesRepository.findAll()));
    }
}