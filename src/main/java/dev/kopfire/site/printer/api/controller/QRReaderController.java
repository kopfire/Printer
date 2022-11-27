package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.service.CartridgesService;
import dev.kopfire.site.printer.core.service.QRCodeService;
import dev.kopfire.site.printer.core.service.TypesCartridgesService;
import dev.kopfire.site.printer.db.repository.TypesCartridgesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class QRReaderController {

    private final Logger logger = LoggerFactory.getLogger(QRReaderController.class);

    private final QRCodeService qrCodeService;

    private final CartridgesService cartridgesService;

    private final TypesCartridgesService typesCartridgesService;

    private final TypesCartridgesRepository typesCartridgesRepository;

    public QRReaderController(QRCodeService qrCodeService, CartridgesService cartridgesService, TypesCartridgesService typesCartridgesService, TypesCartridgesRepository typesCartridgesRepository) {
        this.qrCodeService = qrCodeService;
        this.cartridgesService = cartridgesService;
        this.typesCartridgesService = typesCartridgesService;
        this.typesCartridgesRepository = typesCartridgesRepository;
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
                redirectAttributes.addFlashAttribute("model", typesCartridgesService.getName(cartridgeDTO.getType_cartridge()));
                redirectAttributes.addFlashAttribute("status", cartridgeDTO.getStatus());
                redirectAttributes.addFlashAttribute("comment", cartridgeDTO.getText_status());
                redirectAttributes.addFlashAttribute("typesCartridgesData", typesCartridgesRepository.findAll());
                return "redirect:/qr";
            }
            redirectAttributes.addFlashAttribute("new_cartridge", 1);
            redirectAttributes.addFlashAttribute("qrContent", qrContent);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("typesCartridgesData", typesCartridgesRepository.findAll());

        return "redirect:/qr";
    }

    @PostMapping("/addCartridge")
    public String addCartridge(@RequestParam("name") String name, @RequestParam("model") Long model, @RequestParam("status") String status, @RequestParam("comment") String comment, RedirectAttributes redirectAttributes) {

        CartridgeDTO addCartridgeDTO = new CartridgeDTO(model, name, status, comment);

        cartridgesService.addCartridge(addCartridgeDTO);

        return "redirect:/qr";
    }

    @PostMapping("/changeCartridge")
    public String changeCartridge(@RequestParam("name_change") String name, @RequestParam("status_change") String status, @RequestParam("comment_change") String comment, RedirectAttributes redirectAttributes) {

        CartridgeDTO cartridgeDTO = new CartridgeDTO(1L, name, status, comment);

        cartridgesService.changeCartridge(cartridgeDTO);

        return "redirect:/qr";
    }
}