package com.gungorefe.simpleportfolio.dto.email;

public record EmailDto(
        String email,
        String subject,
        String body
) {
}
