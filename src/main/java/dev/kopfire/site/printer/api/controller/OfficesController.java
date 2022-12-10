package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.core.service.HousingsService;
import dev.kopfire.site.printer.core.service.OfficesService;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/deleteOffice")
    public String deleteOffice(@RequestParam("id_del") Long id_del) {

        officesService.deleteOffice(id_del);

        return "redirect:/offices";
    }

    @PostMapping("/changeOffice")
    public String changeOffice(@RequestParam("id_change") Long id_change, @RequestParam("name_change") String name_change, @RequestParam("housings_change") Long housings_change, RedirectAttributes redirectAttributes) {

        if (!name_change.matches("[a-zA-Z0-9-]+")){
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректные символы (Кириллица запрещена)");
            return "redirect:/offices";
        }

        HousingsDTO housingsDTO = housingsService.getHousingDTO(housings_change);

        if(officesService.officeAlreadyExists(housingsDTO, name_change)){
            redirectAttributes.addFlashAttribute("errorMessage", "Этот кабинет уже добавлен");
            return "redirect:/offices";
        }

        officesService.changeOffice(id_change, name_change, housingsDTO);

        return "redirect:/offices";
    }
}