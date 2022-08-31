package com.messages.mngr.controller;

import com.messages.mngr.services.interfaces.ITranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // NOSONAR
@RequestMapping("translate")
public class TranslateController {

    private ITranslateService translateService;

    @Autowired
    public void setTranslateService(ITranslateService translateService) {
        this.translateService = translateService;
    }

    @GetMapping("/decodeBits")
    public String decodeBits2Morse() {
        return "";
    }

    @GetMapping("/2text")
    public String translate2Human() {
        return "";
    }
}
