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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.messages.mngr.utils.ConstantsUtil.*;
import static com.messages.mngr.utils.ValidateUtil.validateInput;

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
    public String translate2Human(String messageMorse) throws TranslateException {
        List<String> morseWordsArray = List.of(messageMorse.split(DOUBLE_BLANK_CHAR));
        List<String> finalMessage = new ArrayList<>();
        morseWordsArray.forEach(word -> {
            List<String> morseLettersArray = List.of(word.split(BLANK_CHAR));
            morseLettersArray.forEach(letter -> finalMessage.add(charactersList.get(letter)));
            finalMessage.add(BLANK_CHAR);
        });
        if (finalMessage.isEmpty() || finalMessage.contains(null)) {
            throw new TranslateException(HttpStatus.BAD_REQUEST, "There's an error in the message. No valid morse symbols were found");
        }
        return String.join(EMPTY_STRING, finalMessage).trim();
    }

    @Override
    public String decodeBits2Morse(String bits) throws TranslateException {
        validateInput(bits);
        List<String> pulses = new LinkedList<>(List.of(bits.split("(?<=0)(?=1)|(?<=1)(?=0)")));
        trimPulsesList(pulses);
        return getFinalMessageAsMorse(pulses);
    }

    private void trimPulsesList(List<String> pulses) {
        pulses.remove(ZERO_CONSTANT);
        pulses.remove(pulses.size() - 1);
    }

    private String getFinalMessageAsMorse(List<String> pulses) throws TranslateException {
        double average = findPulsesAverage(pulses);
        List<String> finalMessage = new ArrayList<>();
        pulses.forEach(pulse -> {
            if (String.valueOf(pulse.charAt(ZERO_CONSTANT)).equals(String.valueOf(ONE_CONSTANT))) {
                finalMessage.add(isGreaterThanAverage(pulse.length(), average) ? HYPHEN : DOT);
            } else {
                finalMessage.add(isGreaterThanAverage(pulse.length(), average) ? BLANK_CHAR : EMPTY_STRING);
            }
        });
        return String.join(EMPTY_STRING, finalMessage);
    }

    private double findPulsesAverage(List<String> pulses) throws TranslateException {
        List<Integer> pulsesLength = pulses.stream().map(String::length).collect(Collectors.toList());
        return pulsesLength.stream().mapToDouble(pulse -> pulse).average().orElseThrow(() -> new TranslateException(HttpStatus.INTERNAL_SERVER_ERROR, "Error when it gets pulses averages."));
    }

    private boolean isGreaterThanAverage(int number, Double average) {
        return number > average;
    }
}
