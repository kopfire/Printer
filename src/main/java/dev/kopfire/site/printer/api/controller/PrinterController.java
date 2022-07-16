package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.PersonDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrinterController {

    @GetMapping("/printer")
    public String show() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDTO personDetails = (PersonDTO)authentication.getPrincipal();
        return "printer";
    }


}