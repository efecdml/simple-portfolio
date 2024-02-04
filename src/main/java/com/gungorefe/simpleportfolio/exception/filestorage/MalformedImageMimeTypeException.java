package com.gungorefe.simpleportfolio.exception.filestorage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MalformedImageMimeTypeException extends RuntimeException {
    public MalformedImageMimeTypeException(String message) {
        super(message);
    }
}
