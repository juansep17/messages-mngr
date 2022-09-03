package co.com.mngr.services;

import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.implementation.TranslateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class TranslateServiceImplTest {

    private static String BINARY = "00000000110110110011100000111111000111111001111110000000111011111111011101110000\n" +
            "00011000111111000001111110011111100000001100001101111111101110111000000110111000\n" +
            "00000000";
    @InjectMocks
    private TranslateServiceImpl translateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void translate2HumanTest() throws TranslateException {
        String message = translateService.translate2Human(".... --- .-.. .-  -- . .-.. ..");
        Assertions.assertEquals("HOLA MELI", message);
    }

    @Test
    void translate2HumanBadRequestTest() {
        try {
            translateService.translate2Human(".... --/- .-.. .-  -- . .-.. ..");
        } catch (TranslateException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }

    @Test
    void decodeBits2MorseTest() throws TranslateException {
        String message = translateService.decodeBits2Morse(BINARY);
        String humanLanguageMessage = translateService.translate2Human(message);
        Assertions.assertEquals("HOLAMELI", humanLanguageMessage);
    }

    @Test
    void decodeBits2MorseBadRequestTest() {
        try {
            translateService.decodeBits2Morse(BINARY);
        } catch (TranslateException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getHttpStatus());
        }
    }
}
