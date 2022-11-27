package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class CartridgesController {

    private final CartridgesRepository cartridgesRepository;

    public CartridgesController(CartridgesRepository cartridgesRepository) {
        this.cartridgesRepository = cartridgesRepository;
    }

    @GetMapping("/cartridges")
    public ModelAndView cartridgesPage() {
        return new ModelAndView("/cartridges", Collections.singletonMap("cartridgesData", cartridgesRepository.findAll()));
    }
}