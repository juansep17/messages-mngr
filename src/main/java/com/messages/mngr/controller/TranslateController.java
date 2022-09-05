package com.messages.mngr.controller;

import com.messages.mngr.dto.RequestDto;
import com.messages.mngr.dto.ResponseDto;
import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.interfaces.ITranslateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // NOSONAR
@RequestMapping("translate")
public class TranslateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateController.class);

    private ITranslateService translateService;

    @Autowired
    public void setTranslateService(ITranslateService translateService) {
        this.translateService = translateService;
    }

    @PostMapping(value = "/decodeBits")
    public ResponseEntity<ResponseDto> decodeBits2Morse(@RequestBody RequestDto bits) {
        try {
            LOGGER.info("START DECODE BITS -> {}", bits.getText());
            String decodedMessage = translateService.decodeBits2Morse(bits.getText());
            LOGGER.info("FINISH DECODE BITS -> {}", decodedMessage);
            return ResponseEntity.ok(buildResponseDto(decodedMessage, HttpStatus.OK));
        } catch (TranslateException e) {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(buildResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/2text")
    public ResponseEntity<ResponseDto> translate2Human(@RequestBody RequestDto morseCode) {
        try {
            LOGGER.info("START TRANSLATE TO HUMAN -> {}", morseCode.getText());
            String decodedMessage = translateService.translate2Human(morseCode.getText());
            LOGGER.info("FINISH TRANSLATE TO HUMAN -> {}", decodedMessage);
            return ResponseEntity.ok(buildResponseDto(decodedMessage, HttpStatus.OK));
        } catch (TranslateException e) {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(buildResponseDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseDto buildResponseDto(String message, HttpStatus status) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        responseDto.setHttpStatus(status.value());
        return responseDto;
    }
}
