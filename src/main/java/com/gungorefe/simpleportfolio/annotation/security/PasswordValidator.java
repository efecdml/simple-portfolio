package com.gungorefe.simpleportfolio.annotation.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    /* Expects string of 6-12 characters that contains at least one digit, one lowercase and one uppercase character. */
    private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$";

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(REGEX)
                .matcher(password)
                .matches();
    }
}
