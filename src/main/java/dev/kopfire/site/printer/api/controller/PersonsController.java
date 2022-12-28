package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.service.PersonDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/deletePerson")
    public String deletePerson(@RequestParam("id_del") Integer id_del) {

        personService.deletePerson(id_del);

        return "redirect:/persons";
    }

    @PostMapping("/changePerson")
    public String changePerson(@RequestParam("id_change") int id_change, @RequestParam("role_change") String role_change, RedirectAttributes redirectAttributes) {

        personService.changePerson(id_change, role_change);

        return "redirect:/persons";
    }
}
