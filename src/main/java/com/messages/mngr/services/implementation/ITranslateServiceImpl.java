package com.messages.mngr.services.implementation;

import com.messages.mngr.services.interfaces.ITranslateService;
import org.springframework.stereotype.Service;

@Service
public class ITranslateServiceImpl implements ITranslateService {

    @Override
    public String decodeBits2Morse(String bits) {
        return null;
    }

    @Override
    public String translate2Human(String messageMorse) {
        return null;
    }
}
