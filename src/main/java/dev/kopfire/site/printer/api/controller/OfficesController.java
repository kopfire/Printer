package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.HousingsService;
import dev.kopfire.site.printer.core.service.OfficesService;
import dev.kopfire.site.printer.db.repository.HousingsRepository;
import dev.kopfire.site.printer.db.repository.OfficesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class OfficesController {

    private final OfficesService officesService;

    private final HousingsService housingsService;

    public OfficesController(OfficesService officesService,
                             HousingsService housingsService) {
        this.officesService = officesService;
        this.housingsService = housingsService;
    }

    @GetMapping("/offices")
    public ModelAndView officesPage() {
        Map<String, Object> model = new HashMap<>();
        model.put("officesData", officesService.findAll());
        model.put("housingsData", housingsService.findAll());

        return new ModelAndView("offices", "model", model);
    }

    @PostMapping("/addOffice")
    public String addOffice(@RequestParam("name") String name, @RequestParam("housings") Long housings, RedirectAttributes redirectAttributes) {



        if (!name.matches("[a-zA-Z0-9-]+")){
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/offices";
        }

        HousingsDTO housingsDTO = housingsService.getHousingDTO(housings);

        if(officesService.officeAlreadyExists(housingsDTO, name)){
            redirectAttributes.addFlashAttribute("errorMessage", "Этот кабинет уже добавлен");
            return "redirect:/offices";
        }

        OfficesDTO addOfficeDTO = new OfficesDTO(name, housingsDTO);

        officesService.addOffice(addOfficeDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Кабинет успешно добавлен");

        return "redirect:/offices";
    }
}