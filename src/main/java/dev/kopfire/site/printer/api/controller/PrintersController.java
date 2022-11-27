package dev.kopfire.site.printer.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrintersController {

    @GetMapping("/printers")
    public String printersPage() {
        return "printers";
    }
}