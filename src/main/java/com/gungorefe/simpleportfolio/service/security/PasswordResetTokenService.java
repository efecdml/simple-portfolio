package com.gungorefe.simpleportfolio.service.security;

import com.gungorefe.simpleportfolio.config.email.EmailServiceProperties;
import com.gungorefe.simpleportfolio.config.security.SecurityProperties;
import com.gungorefe.simpleportfolio.dto.email.EmailDto;
import com.gungorefe.simpleportfolio.entity.security.PasswordResetToken;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.security.PasswordResetTokenRepository;
import com.gungorefe.simpleportfolio.service.email.EmailService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository repository;
    private final EmailService emailService;
    private final String serverAddress;
    private final Duration tokenExpiration;
    private final MessageSource messageSource;

    public PasswordResetTokenService(PasswordResetTokenRepository repository, EmailService emailService, EmailServiceProperties emailServiceProperties, SecurityProperties securityProperties, MessageSource securityMessageSource) {
        this.repository = repository;
        this.emailService = emailService;
        this.serverAddress = emailServiceProperties.serverAddress();
        this.tokenExpiration = securityProperties.passwordResetTokenExpirationDuration();
        messageSource = securityMessageSource;
    }

    public void send(User user) {
        Optional<PasswordResetToken> currentToken = repository.findByUser_Email(user.getEmail());

        if (currentToken.isEmpty())
            createNewAndSend(user);
        else
            sendExisting(
                    currentToken.get(),
                    user.getEmail()
            );
    }

    public PasswordResetToken getToken(UUID token) {
        PasswordResetToken resetToken = repository.findById(token)
                .orElseThrow(() -> ExceptionFactory.invalidPasswordResetTokenException(token));

        if (isExpired(resetToken))
            throw ExceptionFactory.expiredPasswordResetTokenException(token);

        return resetToken;
    }

    private void createNewAndSend(User user) {
        PasswordResetToken token = create(user);

        sendEmail(
                token,
                user.getEmail()
        );
    }

    private void sendExisting(
            PasswordResetToken token,
            String email
    ) {
        if (token.isSent()) {
            Duration timeLeft = Duration.between(token.getExpirationDate(), LocalDateTime.now());

            throw ExceptionFactory.passwordResetEmailAlreadySentException(
                    email,
                    timeLeft
            );
        }

        sendEmail(
                token,
                email
        );
    }

    private boolean isExpired(PasswordResetToken token) {
        return token.getExpirationDate().isBefore(LocalDateTime.now());
    }

    private PasswordResetToken create(User user) {
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(tokenExpiration.toMillis());

        PasswordResetToken token = new PasswordResetToken(
                UUID.randomUUID(),
                expirationDate,
                false,
                user
        );

        return repository.save(token);
    }

    private void sendEmail(
            PasswordResetToken token,
            String email
    ) {
        String body = messageSource.getMessage(
                "password.reset.link",
                null,
                LocaleContextHolder.getLocale()
        );
        EmailDto dto = new EmailDto(
                email,
                body,
                generateUrl(token.getId())
        );

        emailService.send(dto);
    }

    private String generateUrl(UUID token) {
        return String.join(
                "/",
                serverAddress,
                "api/session/password-reset/token",
                token.toString()
        );
    }
}
