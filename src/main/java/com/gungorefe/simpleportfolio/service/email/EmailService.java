package com.gungorefe.simpleportfolio.service.email;

import com.gungorefe.simpleportfolio.config.email.EmailServiceProperties;
import com.gungorefe.simpleportfolio.dto.email.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String from;

    public EmailService(JavaMailSender mailSender, EmailServiceProperties properties) {
        this.mailSender = mailSender;
        from = properties.serverEmail();
    }

    @Async
    public void send(EmailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(dto.email());
        message.setSubject(dto.subject());
        message.setText(dto.body());

        mailSender.send(message);
    }
}
