package com.messages.mngr.services.interfaces;

import com.messages.mngr.exceptions.TranslateException;

public interface ITranslateService {

    String decodeBits2Morse(String bits);

    String translate2Human(String messageMorse) throws TranslateException;
}
