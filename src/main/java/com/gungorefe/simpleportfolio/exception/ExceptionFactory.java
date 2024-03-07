package com.gungorefe.simpleportfolio.exception;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.exception.filestorage.MalformedImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageNameException;
import com.gungorefe.simpleportfolio.exception.page.LocaleNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.PageNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.component.ComponentNotFoundException;
import com.gungorefe.simpleportfolio.exception.security.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionFactory {
    public static MalformedImageMimeTypeException malformedImageMimeTypeException(String mimeType) {
        return new MalformedImageMimeTypeException("Image could not be processed due to unsupported MIME type: " + mimeType);
    }

    public static UnacceptableImageNameException unacceptableImageNameException(String imageName) {
        return new UnacceptableImageNameException("Unacceptable image name: " + imageName);
    }

    public static UnacceptableImageMimeTypeException unacceptableImageMimeTypeException(String mimeType) {
        return new UnacceptableImageMimeTypeException("Unacceptable MIME type: " + mimeType);
    }

    public static PageNotFoundException pageNotFoundException(
            String pageName,
            LocaleName localeName
    ) {
        return new PageNotFoundException(MessageFormat.format(
                "Page: {0} not found by locale: {1}",
                pageName,
                localeName
        ));
    }

    public static LocaleNotFoundException localeNotFoundException(String localeName) {
        return new LocaleNotFoundException("Locale not found: " + localeName);
    }

    public static ComponentNotFoundException componentNotFoundException(
            String componentName,
            int id
    ) {
        return new ComponentNotFoundException(MessageFormat.format(
                "Component: {0} not found by ID: {1}",
                componentName,
                id
        ));
    }

    public static BadCredentialsException badCredentialsException() {
        return new BadCredentialsException("Bad credentials");
    }

    public static BruteForceAuthenticationAttemptException bruteForceAuthenticationAttemptException() {
        return new BruteForceAuthenticationAttemptException("Login service is forbidden for this client for 24 hours.");
    }

    public static UserNotFoundException userNotFoundException(String email) {
        return new UserNotFoundException(MessageFormat.format(
                "User with email: {0} not found",
                email
        ));
    }

    public static RoleNotFoundException roleNotFoundException(RoleName roleName) {
        return new RoleNotFoundException("Role not found: " + roleName.name());
    }

    public static EmailAlreadyInUseException emailAlreadyInUseException(String email) {
        return new EmailAlreadyInUseException("Email is already in use: " + email);
    }

    public static PasswordResetEmailAlreadySentException passwordResetEmailAlreadySentException(
            String email,
            Duration timeLeft
    ) {
        return new PasswordResetEmailAlreadySentException(MessageFormat.format(
                "Email for {0} is already sent. {1} minutes left to send again.",
                email,
                timeLeft.toMinutesPart() + ":" + timeLeft.toSecondsPart()
        ));
    }

    public static InvalidPasswordResetTokenException invalidPasswordResetTokenException(UUID token) {
        return new InvalidPasswordResetTokenException("Invalid password reset token: " + token);
    }

    public static ExpiredPasswordResetTokenException expiredPasswordResetTokenException(UUID token) {
        return new ExpiredPasswordResetTokenException("Expired password reset token: " + token);
    }
}
