package com.messages.mngr.controller;

import com.messages.mngr.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> decodeBits2Morse(@RequestBody String bits) {
        try {
            String decodedMessage = translateService.decodeBits2Morse(bits);
            return ResponseEntity.ok(buildResponseDto(decodedMessage, HttpStatus.OK));
        } catch (TranslateException e) {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/2text")
    public ResponseEntity<ResponseDto> translate2Human(@RequestBody String morseCode) {
        try {
            String decodedMessage = translateService.translate2Human(morseCode);
            return ResponseEntity.ok(buildResponseDto(decodedMessage, HttpStatus.OK));
        } catch (TranslateException e) {
            if (e.getHttpStatus().equals(HttpStatus.BAD_REQUEST)) {
                return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(buildResponseDto(e.getMessage(), e.getHttpStatus()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseDto buildResponseDto(String message, HttpStatus status) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        responseDto.setHttpStatus(status.value());
        return responseDto;
    }
}
