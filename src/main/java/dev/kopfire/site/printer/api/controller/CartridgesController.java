package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.service.CartridgesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class CartridgesController {

    private final CartridgesService cartridgesService;

    public CartridgesController(CartridgesService cartridgesService) {
        this.cartridgesService = cartridgesService;
    }

    @GetMapping("/cartridges")
    public ModelAndView cartridgesPage() {
        return new ModelAndView("/cartridges", Collections.singletonMap("cartridgesData", cartridgesService.findAll()));
    }

    /*@PostMapping("/addCartridge")
    public String addCartridge(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {


        if (!name.matches("[a-zA-Z0-9-]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/cartridges";
        }

        if (cartridgesService.cartridgeAlreadyExists(name)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Этот картридж уже добавлен");
            return "redirect:/cartridges";
        }

        return "redirect:/cartridges";
    }*/
}