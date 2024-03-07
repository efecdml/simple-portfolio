package com.gungorefe.simpleportfolio.dto.security;

import com.gungorefe.simpleportfolio.annotation.FieldsValueMatch;

@FieldsValueMatch(
        field = "password",
        fieldToMatch = "confirmedPassword",
        message = "Passwords do not match"
)
public record UpdatePasswordRequest(
        String password,
        String confirmedPassword
) {
}
