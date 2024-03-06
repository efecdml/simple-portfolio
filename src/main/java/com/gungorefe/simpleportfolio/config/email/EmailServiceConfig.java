package com.gungorefe.simpleportfolio.config.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@RequiredArgsConstructor
@Configuration
public class EmailServiceConfig {
    private final EmailServiceProperties configProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configProperties.host());
        mailSender.setPort(configProperties.port());
        mailSender.setUsername(configProperties.username());
        mailSender.setPassword(configProperties.password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", configProperties.mailTransportProtocol());
        properties.put("mail.smtp.auth", configProperties.mailSmtpAuth());
        properties.put("mail.smtp.starttls.enable", configProperties.mailSmtpStarttlsEnable());
        properties.put("mail.debug", configProperties.mailDebug());

        return mailSender;
    }
}
