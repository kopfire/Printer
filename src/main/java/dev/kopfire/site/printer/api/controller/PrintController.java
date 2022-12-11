package dev.kopfire.site.printer.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
public class PrintController {

    @PostMapping("/print")
    public ModelAndView printPage(HttpServletRequest request) {

        List<String> list = (List<String>) request.getAttribute("list");
        return new ModelAndView("/print", Collections.singletonMap("imageData", list));
    }
}