package com.gungorefe.simpleportfolio.service.security;

import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final UserRepository userRepository;

    public User getUser(String email) {
        return userRepository.findWithRoleByEmail(email)
                .orElseThrow(ExceptionFactory::badCredentialsException);
    }
}
