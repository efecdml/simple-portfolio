package com.gungorefe.simpleportfolio.service.security;

import com.gungorefe.simpleportfolio.config.security.SecurityProperties;
import com.gungorefe.simpleportfolio.dto.security.LoginRequest;
import com.gungorefe.simpleportfolio.dto.security.LoginResponse;
import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.dto.security.UpdatePasswordRequest;
import com.gungorefe.simpleportfolio.entity.security.PasswordResetToken;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.util.WebUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final String accessTokenCookieName;
    private final Duration jwtExpiration;

    public SessionService(AuthenticationManager authManager, JwtService jwtService, SecurityService securityService, PasswordResetTokenService passwordResetTokenService, PasswordEncoder passwordEncoder, SecurityProperties securityProperties) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.securityService = securityService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
        accessTokenCookieName = securityProperties.accessTokenCookieName();
        jwtExpiration = securityProperties.jwtExpirationDuration();
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        authManager.authenticate(token);

        User user = securityService.getUser(
                true,
                request.email()
        );
        RoleName roleName = user.getRole().getName();
        String accessToken = jwtService.generateToken(
                Map.of("role", roleName.name()),
                user
        );
        ResponseCookie cookie = WebUtils.createCookie(
                accessTokenCookieName,
                accessToken,
                jwtExpiration
        );

        return new LoginResponse(
                cookie,
                roleName
        );
    }

    public ResponseCookie logout() {
        return WebUtils.deleteCookie(accessTokenCookieName);
    }

    public void createAndSendPasswordResetToken(String email) {
        User user = securityService.getUser(
                true,
                email
        );

        passwordResetTokenService.send(user);
    }

    public void resetPassword(
            UUID token,
            UpdatePasswordRequest request
    ) {
        PasswordResetToken resetToken = passwordResetTokenService.getToken(token);
        User user = resetToken.getUser();
        String newPassword = passwordEncoder.encode(request.password());
        user.setPassword(newPassword);

        securityService.saveUser(user);
    }
}
