package com.gungorefe.simpleportfolio.dto.security;

public record LoginRequest(
        String email,
        String password
) {
}
