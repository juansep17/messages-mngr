package com.messages.mngr.services.interfaces;

public interface ITranslateService {

    String decodeBits2Morse(String bits);

    String translate2Human(String messageMorse);
}
