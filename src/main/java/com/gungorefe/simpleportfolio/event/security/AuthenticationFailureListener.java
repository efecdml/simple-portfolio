package com.gungorefe.simpleportfolio.event.security;

import com.gungorefe.simpleportfolio.service.security.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(@NonNull AuthenticationFailureBadCredentialsEvent event) {
        loginAttemptService.failedLogin();
    }
}
