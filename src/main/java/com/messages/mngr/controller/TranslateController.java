package com.messages.mngr.controller;

import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.interfaces.ITranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> translate2Human(@RequestBody String morseCode) {
        try {
            String decodedMessage = translateService.translate2Human(morseCode);
            return ResponseEntity.ok(decodedMessage);
        } catch (TranslateException e) {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                //return ResponseEntity.badRequest();
            }
            //return ResponseEntity.internalServerError();
            return null;
        }
    }
}
