package com.messages.mngr.utils;

import com.messages.mngr.exceptions.TranslateException;
import org.springframework.http.HttpStatus;

public class ValidateUtil {

    public static void validateInput(String input) throws TranslateException {
        String scapedString = input.replace("\n", "");
        if (!scapedString.matches("[01]*")) {
            throw new TranslateException(HttpStatus.BAD_REQUEST, "There's an error in the message. No valid binary symbols were found");
        }
    }
}
