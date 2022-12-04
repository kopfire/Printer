package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.service.PersonDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class PersonsController {

    private final PersonDetailsService personService;

    public PersonsController(PersonDetailsService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public ModelAndView usersPage() {
        return new ModelAndView("/persons", Collections.singletonMap("personsData", personService.findAll()));
    }
}
