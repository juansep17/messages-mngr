package com.messages.mngr.exceptions;

import org.springframework.http.HttpStatus;

public class TranslateException extends Exception {

    private final HttpStatus httpStatus;

    public TranslateException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public TranslateException(HttpStatus httpStatus, String causeMessage) {
        super(causeMessage);
        this.httpStatus = httpStatus;
    }

    public TranslateException(HttpStatus httpStatus, String causeMessage, Exception e) {
        super(causeMessage, e);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
