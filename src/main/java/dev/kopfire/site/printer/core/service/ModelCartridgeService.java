package dev.kopfire.site.printer.core.service;

import org.springframework.stereotype.Service;

@Service
public class ModelCartridgeService {

    public String getName(int type) {
        if (type == 1)
            return "12A";
        return "";
    }

    public String getPlace(int type) {
        if (type == 1)
            return "В отделе";
        if (type == 2)
            return "На заправке";
        if (type == 3)
            return "У пользователей";
        return "";
    }
}
