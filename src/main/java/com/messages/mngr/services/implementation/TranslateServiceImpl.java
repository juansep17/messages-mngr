package com.messages.mngr.services.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.messages.mngr.dto.CharactersDto;
import com.messages.mngr.exceptions.TranslateException;
import com.messages.mngr.services.interfaces.ITranslateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class TranslateServiceImpl implements ITranslateService {

    private static final String BLANK_CHAR = " ";
    private static final String DOUBLE_BLANK_CHAR = "  ";
    private static final String SYMBOL_DELIMITER = "%";
    private static final String SPACE = "+%";
    private static final String SPACE_IDENTIFIER = "+";
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateServiceImpl.class);
    private static final String PATH = "/json/morseCharacters.json";

    private static List<CharactersDto> charactersList;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, CharactersDto.class);
            InputStream morseCharacters = TranslateServiceImpl.class.getResourceAsStream(PATH);
            charactersList = mapper.readValue(morseCharacters, collectionType);
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
        String messageWithSpacesBetweenWords = messageMorse.replace(DOUBLE_BLANK_CHAR, SPACE);
        String[] morseSymbolsArray = messageWithSpacesBetweenWords.replace(BLANK_CHAR, SYMBOL_DELIMITER).split(SYMBOL_DELIMITER);
        List<String> decodedCharacters = getMessageInList(morseSymbolsArray);
        if (decodedCharacters.isEmpty()) {
            throw new TranslateException(HttpStatus.BAD_REQUEST, "No valid morse symbols were found");
        }
        return String.join("", decodedCharacters);
    }

    private List<String> getMessageInList(String[] encodedMessageArray) {
        List<String> decodedMessage = new ArrayList<>();
        Arrays.stream(encodedMessageArray).forEach(encodedSymbol -> charactersList.forEach(character -> {
            boolean hasSpace = encodedSymbol.contains(SPACE_IDENTIFIER);
            String encodedSymbolVerified = hasSpace ? encodedSymbol.substring(0, encodedSymbol.length() - 1) : encodedSymbol;
            if (character.getMorseSymbol().equals(encodedSymbolVerified)) {
                decodedMessage.add(hasSpace ? character.getCharacter().concat(BLANK_CHAR) : character.getCharacter());
            }
        }));
        return decodedMessage;
    }
}
