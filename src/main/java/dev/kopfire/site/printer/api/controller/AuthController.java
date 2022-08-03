package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.PersonDTO;
import dev.kopfire.site.printer.core.service.RegistrationService;
import dev.kopfire.site.printer.core.util.PersonValidator;
import dev.kopfire.site.printer.db.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") PersonDTO person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid PersonDTO person,
            BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";


        registrationService.register(person);

        return "redirect:/auth/login";
    }
}
