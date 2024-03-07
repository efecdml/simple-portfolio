package com.gungorefe.simpleportfolio.exception;

import com.gungorefe.simpleportfolio.exception.filestorage.MalformedImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageNameException;
import com.gungorefe.simpleportfolio.exception.page.LocaleNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.PageNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.component.ComponentNotFoundException;
import com.gungorefe.simpleportfolio.exception.security.*;
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

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<String> pageNotFoundException(PageNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(LocaleNotFoundException.class)
    public ResponseEntity<String> localeNotFoundException(LocaleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ComponentNotFoundException.class)
    public ResponseEntity<String> componentNotFoundException(ComponentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BruteForceAuthenticationAttemptException.class)
    public ResponseEntity<String> bruteForceAuthenticationAttemptException(BruteForceAuthenticationAttemptException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> roleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> emailAlreadyInUseException(EmailAlreadyInUseException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PasswordResetEmailAlreadySentException.class)
    public ResponseEntity<String> passwordRecoveryEmailAlreadySentException(PasswordResetEmailAlreadySentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<String> invalidPasswordRecoveryTokenException(InvalidPasswordResetTokenException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ExpiredPasswordResetTokenException.class)
    public ResponseEntity<String> expiredPasswordRecoveryTokenException(ExpiredPasswordResetTokenException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }
}
