package com.gungorefe.simpleportfolio.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ExpiredPasswordResetTokenException extends RuntimeException {
    public ExpiredPasswordResetTokenException(String message) {
        super(message);
    }
}
