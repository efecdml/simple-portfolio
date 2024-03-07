package com.gungorefe.simpleportfolio.exception.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BruteForceAuthenticationAttemptException extends RuntimeException {
    public BruteForceAuthenticationAttemptException(String message) {
        super(message);
    }
}
