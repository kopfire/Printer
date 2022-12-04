package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.OfficesDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class QRReaderController {

    private final Logger logger = LoggerFactory.getLogger(QRReaderController.class);

    private final QRCodeService qrCodeService;

    private final CartridgesService cartridgesService;

    private final TypesCartridgesService typesCartridgesService;

    private final OfficesService officesService;

    private final HousingsService housingsService;

    public QRReaderController(QRCodeService qrCodeService,
                              CartridgesService cartridgesService,
                              TypesCartridgesService typesCartridgesService,
                              OfficesService officesService,
                              HousingsService housingsService) {
        this.qrCodeService = qrCodeService;
        this.cartridgesService = cartridgesService;
        this.typesCartridgesService = typesCartridgesService;
        this.officesService = officesService;
        this.housingsService = housingsService;
    }

    @GetMapping("/qr")
    public String qrPage() {
        return "qr";
    }

    @PostMapping("/uploadQrCode")
    public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile, RedirectAttributes redirectAttributes) {

        if (qrCodeFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Файл не выбран.");
            return "redirect:/qr";
        }
        try {

            String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());

            if (qrContent == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Файл не содержит QR кода.");
                return "redirect:/qr";
            }

            if (!qrContent.startsWith("АГУ_Картридж")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Некорректный QR код.");
                return "redirect:/qr";
            }

            CartridgeDTO cartridgeDTO = cartridgesService.getCartridge(qrContent);

            if (cartridgeDTO != null) {
                redirectAttributes.addFlashAttribute("name", qrContent);
                redirectAttributes.addFlashAttribute("model", cartridgeDTO.getType_cartridge().getName());
                redirectAttributes.addFlashAttribute("status", cartridgeDTO.getStatus());
                redirectAttributes.addFlashAttribute("comment", cartridgeDTO.getText_status());
                redirectAttributes.addFlashAttribute("office", cartridgeDTO.getOffice());
                redirectAttributes.addFlashAttribute("typesCartridgesData", typesCartridgesService.findAll());
                redirectAttributes.addFlashAttribute("housingsData", housingsService.findAll());
                redirectAttributes.addFlashAttribute("officesData", officesService.findAll());
                return "redirect:/qr";
            }

            redirectAttributes.addFlashAttribute("qrContent", qrContent);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("typesCartridgesData", typesCartridgesService.findAll());
        redirectAttributes.addFlashAttribute("housingsData", housingsService.findAll());
        redirectAttributes.addFlashAttribute("officesData", officesService.findAll());

        return "redirect:/qr";
    }

    @PostMapping("/addCartridge")
    public String addCartridge(@RequestParam("name") String name, @RequestParam("model") Long model, @RequestParam("status") String status, @RequestParam("comment") String comment, @RequestParam("housings") Long housings, @RequestParam("offices") Long offices, RedirectAttributes redirectAttributes) {

        TypesCartridgesDTO modelDTO = typesCartridgesService.getById(model);

        if (status.equals("У пользователей")) {
            OfficesDTO officeDTO = officesService.getOffice(offices);

            if (!Objects.equals(officeDTO.getHousing().getId(), housings)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Некорректный кабинет.");
                redirectAttributes.addFlashAttribute("qrContent", name);
                redirectAttributes.addFlashAttribute("typesCartridgesData", typesCartridgesService.findAll());
                redirectAttributes.addFlashAttribute("housingsData", housingsService.findAll());
                redirectAttributes.addFlashAttribute("officesData", officesService.findAll());
                return "redirect:/qr";
            }

            CartridgeDTO addCartridgeDTO = new CartridgeDTO(modelDTO, name, status, comment, officeDTO);
            cartridgesService.addCartridge(addCartridgeDTO);
            redirectAttributes.addFlashAttribute("successMessage", name + " успешно добавлен");
            return "redirect:/qr";
        }

        CartridgeDTO addCartridgeDTO = new CartridgeDTO(modelDTO, name, status, comment, null);

        cartridgesService.addCartridge(addCartridgeDTO);

        redirectAttributes.addFlashAttribute("successMessage", name + " успешно добавлен");

        return "redirect:/qr";
    }

    @PostMapping("/changeCartridge")
    public String changeCartridge(@RequestParam("name_change") String name, @RequestParam("status_change") String status, @RequestParam("comment_change") String comment, @RequestParam("housings_change") Long housings, @RequestParam("offices") Long offices, RedirectAttributes redirectAttributes) {

        if (status.equals("У пользователей")) {
            OfficesDTO officeDTO = officesService.getOffice(offices);

            if (!Objects.equals(officeDTO.getHousing().getId(), housings)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Некорректный кабинет.");
                /*redirectAttributes.addFlashAttribute("status_change", status);
                redirectAttributes.addFlashAttribute("name", name);
                redirectAttributes.addFlashAttribute("housingsData", housingsService.findAll());
                redirectAttributes.addFlashAttribute("officesData", officesService.findAll());*/
                return "redirect:/qr";
            }

            CartridgeDTO cartridgeDTO = new CartridgeDTO(null, name, status, comment, officeDTO);
            cartridgesService.changeCartridge(cartridgeDTO);
            redirectAttributes.addFlashAttribute("successMessage", name + " успешно изменен");
            return "redirect:/qr";
        }

        CartridgeDTO cartridgeDTO = new CartridgeDTO(null, name, status, comment, null);

        cartridgesService.changeCartridge(cartridgeDTO);

        redirectAttributes.addFlashAttribute("successMessage", name + " успешно изменен");

        return "redirect:/qr";
    }
}