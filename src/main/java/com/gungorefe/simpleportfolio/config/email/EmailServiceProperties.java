package com.gungorefe.simpleportfolio.config.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
public record EmailServiceProperties(
        String serverEmail,
        String host,
        int port,
        String username,
        String password,
        String mailTransportProtocol,
        boolean mailSmtpAuth,
        boolean mailSmtpStarttlsEnable,
        boolean mailDebug,
        String serverAddress
) {
}
