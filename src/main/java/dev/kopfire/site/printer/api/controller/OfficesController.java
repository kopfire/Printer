package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.db.repository.OfficesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class OfficesController {

    private final OfficesRepository officesRepository;

    public OfficesController(OfficesRepository officesRepository) {
        this.officesRepository = officesRepository;
    }

    @GetMapping("/offices")
    public ModelAndView officesPage() {
        return new ModelAndView("/offices", Collections.singletonMap("officesData", officesRepository.findAll()));
    }
}