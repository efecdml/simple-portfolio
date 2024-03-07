package com.gungorefe.simpleportfolio.dto.security;

import com.gungorefe.simpleportfolio.annotation.FieldsValueMatch;
import com.gungorefe.simpleportfolio.annotation.security.Password;
import jakarta.validation.constraints.Email;

@FieldsValueMatch(
        field = "password",
        fieldToMatch = "confirmedPassword",
        message = "Passwords do not match"
)
public record CreateUserRequest(
        @Email
        String email,
        @Password
        String password,
        String confirmedPassword,
        RoleName roleName
) {
}
