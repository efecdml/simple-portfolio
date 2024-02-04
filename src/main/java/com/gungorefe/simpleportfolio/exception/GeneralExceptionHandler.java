package com.gungorefe.simpleportfolio.exception;

import com.gungorefe.simpleportfolio.exception.filestorage.MalformedImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(MalformedImageMimeTypeException.class)
    public ResponseEntity<String> malformedImageMimeTypeException(MalformedImageMimeTypeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UnacceptableImageMimeTypeException.class)
    public ResponseEntity<String> unacceptableImageMimeTypeException(UnacceptableImageMimeTypeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UnacceptableImageNameException.class)
    public ResponseEntity<String> unacceptableImageNameException(UnacceptableImageNameException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }
}
