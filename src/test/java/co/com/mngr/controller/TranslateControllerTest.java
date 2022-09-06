package co.com.mngr.controller;

import com.messages.mngr.controller.TranslateController;
import com.messages.mngr.dto.RequestDto;
import com.messages.mngr.dto.ResponseDto;
import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.interfaces.ITranslateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

class TranslateControllerTest {

    @InjectMocks
    private TranslateController translateController;

    @Mock
    private ITranslateService translateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void decodeBits2MorseTest() throws TranslateException {
        Mockito.when(translateService.decodeBits2Morse(Mockito.anyString())).thenReturn("...");
        ResponseEntity<ResponseDto> response = translateController.decodeBits2Morse(buildRequestDto("000000001101101100"));
        Assertions.assertEquals("...", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void decodeBits2MorseBadRequestTest() throws TranslateException {
        Mockito.when(translateService.decodeBits2Morse(Mockito.anyString())).thenThrow(new TranslateException(HttpStatus.BAD_REQUEST, "Error"));
        ResponseEntity<ResponseDto> response = translateController.decodeBits2Morse(buildRequestDto("000000001101/101100"));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void translate2HumanTest() throws TranslateException {
        Mockito.when(translateService.translate2Human(Mockito.anyString())).thenReturn("HOLA MELI");
        ResponseEntity<ResponseDto> response = translateController.translate2Human(buildRequestDto(".... --- .-.. .- -- . .-.. .."));
        Assertions.assertEquals("HOLA MELI", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void translate2HumanBadRequestTest() throws TranslateException {
        Mockito.when(translateService.translate2Human(Mockito.anyString())).thenThrow(new TranslateException(HttpStatus.BAD_REQUEST, "Error"));
        ResponseEntity<ResponseDto> response = translateController.translate2Human(buildRequestDto(".... -$-- .-.. .- -- . .-.. .."));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void translate2MorseTest() throws TranslateException {
        Mockito.when(translateService.translate2Morse(Mockito.anyString())).thenReturn(".... --- .-.. .- -- . .-.. ..");
        ResponseEntity<ResponseDto> response = translateController.translate2Morse(buildRequestDto("HOLA MELI"));
        Assertions.assertEquals(".... --- .-.. .- -- . .-.. ..", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void translate2MorseBadRequestTest() throws TranslateException {
        Mockito.when(translateService.translate2Morse(Mockito.anyString())).thenThrow(new TranslateException(HttpStatus.BAD_REQUEST, "Error"));
        ResponseEntity<ResponseDto> response = translateController.translate2Morse(buildRequestDto(""));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private RequestDto buildRequestDto(String text) {
        RequestDto requestDto = new RequestDto();
        requestDto.setText(text);
        return requestDto;
    }
}
