package dev.kopfire.site.printer.api.controller;

import com.google.zxing.WriterException;
import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class CartridgesController {

    private final CartridgesService cartridgesService;

    private final TypesCartridgesService typesCartridgesService;

    private final QRCodeService qrCodeService;

    private final OfficesService officesService;

    private final HousingsService housingsService;

    public CartridgesController(CartridgesService cartridgesService,
                                TypesCartridgesService typesCartridgesService,
                                QRCodeService qrCodeService,
                                OfficesService officesService,
                                HousingsService housingsService) {
        this.cartridgesService = cartridgesService;
        this.typesCartridgesService = typesCartridgesService;
        this.qrCodeService = qrCodeService;
        this.officesService = officesService;
        this.housingsService = housingsService;
    }

    @GetMapping("/cartridges")
    public ModelAndView cartridgesPage() {

        Map<String, Object> model = new HashMap<>();
        model.put("cartridgesData", cartridgesService.findAll());
        model.put("typesCartridgesData", typesCartridgesService.findAll());
        model.put("housingsData", housingsService.findAll());
        model.put("officesData", officesService.findAll());

        return new ModelAndView("/cartridges", "model", model);
    }

    @PostMapping("/addCartridges")
    public String addCartridges(@RequestParam("typesCartridges") Long typesCartridges, @RequestParam("count") String count, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException, WriterException, InterruptedException {

        if (!count.matches("[0-9]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Количество должно содержать только цифры");
            return "redirect:/cartridges";
        }

        List<CartridgeDTO> listCartridgeDTO = cartridgesService.findAll();

        String lastQr = listCartridgeDTO.get(listCartridgeDTO.size() - 1).getText_qr();

        int first = Integer.parseInt(lastQr.replaceAll("\\D+", "")) + 1;

        int countInt = Integer.parseInt(count);

        TypesCartridgesDTO typesCartridgesDTO = typesCartridgesService.getById(typesCartridges);

        for (int i = 0; i < countInt; i++) {
            String text_qr = "АГУ_Картридж_" + (first + i);
            CartridgeDTO addCartridgeDTO = new CartridgeDTO(typesCartridgesDTO, text_qr, "В отделе", null, null);
            cartridgesService.addCartridge(addCartridgeDTO);
        }

        List<String> list = qrCodeService.generateAndSaveQR(first, Integer.parseInt(count));

        request.setAttribute("list", list);

        return "forward:/print";
    }

    @PostMapping("/deleteCartridge")
    public String deleteCartridge(@RequestParam("id_del") Long id_del) {

        cartridgesService.deleteCartridge(id_del);

        return "redirect:/cartridges";
    }

    @PostMapping("/changeCartridges")
    public String changeCartridge(@RequestParam("id_change") Long id_change, @RequestParam("text_change") String text_change, @RequestParam("name_change") String name_change, @RequestParam("status_change") String status_change, @RequestParam("housings_change") Long housings, @RequestParam("typesCartridges_change") Long typesCartridges_change, @RequestParam("offices") Long offices, RedirectAttributes redirectAttributes) {

        if (!name_change.matches("АГУ_Картридж_[0-9]+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Название содержит некорректный паттерн / лишние символы");
            return "redirect:/cartridges";
        }

        TypesCartridgesDTO typesCartridgesDTO = typesCartridgesService.getById(typesCartridges_change);

        if (status_change.equals("У пользователей")) {
            OfficesDTO officeDTO = officesService.getOffice(offices);

            if (!Objects.equals(officeDTO.getHousing().getId(), housings)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Некорректный кабинет.");
                return "redirect:/cartridges";
            }

            CartridgeDTO cartridgeDTO = new CartridgeDTO(typesCartridgesDTO, name_change, status_change, text_change, officeDTO);
            cartridgesService.changeCartridgeFull(cartridgeDTO, id_change);
            return "redirect:/cartridges";
        }

        CartridgeDTO cartridgeDTO = new CartridgeDTO(typesCartridgesDTO, name_change, status_change, text_change, null);

        cartridgesService.changeCartridgeFull(cartridgeDTO, id_change);


        return "redirect:/cartridges";
    }
}