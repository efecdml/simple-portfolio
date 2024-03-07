package com.gungorefe.simpleportfolio.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties(
        String jwtSecretKey,
        Duration jwtExpirationDuration,
        String accessTokenCookieName,
        int maxLoginAttempt,
        Duration forbiddenLoginDuration,
        Duration passwordResetTokenExpirationDuration
) {
}
