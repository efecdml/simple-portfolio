package com.gungorefe.simpleportfolio.exception.page;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LocaleNotFoundException extends RuntimeException {
    public LocaleNotFoundException(String message) {
        super(message);
    }
}
