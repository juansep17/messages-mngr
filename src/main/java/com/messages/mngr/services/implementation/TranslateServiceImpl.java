package com.messages.mngr.services.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.interfaces.ITranslateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.messages.mngr.utils.ConstantsUtil.BLANK_CHAR;
import static com.messages.mngr.utils.ConstantsUtil.DOUBLE_BLANK_CHAR;

@Service
public class TranslateServiceImpl implements ITranslateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateServiceImpl.class);
    private static final String PATH = "/json/morseCharacters.json";

    private static HashMap<String, String> charactersList;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream morseCharacters = TranslateServiceImpl.class.getResourceAsStream(PATH);
            charactersList = mapper.readValue(morseCharacters, new TypeReference<>() {
            });
            LOGGER.info("Morse characters: {}", charactersList);
        } catch (IOException e) {
            LOGGER.error("Error trying to get morse characters");
        }
    }

    @Override
    public String decodeBits2Morse(String bits) {
        return null;
    }

    @Override
    public String translate2Human(String messageMorse) throws TranslateException {
        String[] morseWordsArray = messageMorse.split(DOUBLE_BLANK_CHAR);
        List<String> finalMessage = new ArrayList<>();
        Arrays.stream(morseWordsArray).forEach(word -> {
            String[] morseLettersArray = word.split(BLANK_CHAR);
            Arrays.stream(morseLettersArray).forEach(letter -> finalMessage.add(charactersList.get(letter)));
            finalMessage.add(BLANK_CHAR);
        });
        if (finalMessage.isEmpty() || finalMessage.contains(null)) {
            throw new TranslateException(HttpStatus.BAD_REQUEST, "There's an error in the message. No valid morse symbols were found");
        }
        return String.join("", finalMessage).trim();
    }
}
