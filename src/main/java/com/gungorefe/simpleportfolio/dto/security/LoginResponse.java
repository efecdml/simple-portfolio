package com.gungorefe.simpleportfolio.dto.security;

import org.springframework.http.ResponseCookie;

public record LoginResponse(
        ResponseCookie cookie,
        RoleName roleName
) {
}
