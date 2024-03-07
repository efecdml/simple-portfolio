package com.gungorefe.simpleportfolio.service.security;

import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.dto.security.UserDto;
import com.gungorefe.simpleportfolio.entity.security.Role;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.security.RoleRepository;
import com.gungorefe.simpleportfolio.repository.security.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LoginAttemptService loginAttemptService;

    @CacheEvict(
            value = "users",
            key = "#user.email"
    )
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /* "secure" parameter is used to make the client unaware of registered users by throwing BadCredentialsException
     * instead of UserNotFoundException. */
    @Cacheable(
            value = "users",
            key = "#email"
    )
    public User getUser(
            boolean secure,
            String email
    ) {
        User user;

        if (secure) {
            if (loginAttemptService.isBlocked())
                throw ExceptionFactory.bruteForceAuthenticationAttemptException();

            user = userRepository.findWithRoleByEmail(email)
                    .orElseThrow(ExceptionFactory::bruteForceAuthenticationAttemptException);
        } else {
            user = userRepository.findWithRoleByEmail(email)
                    .orElseThrow(() -> ExceptionFactory.userNotFoundException(email));
        }

        return user;
    }

    @Cacheable(
            value = "userRoles",
            key = "#roleName.name()"
    )
    public Role getRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> ExceptionFactory.roleNotFoundException(roleName));
    }

    public Set<UserDto> getAllUsers() {
        Set<User> users = userRepository.findAllForDto();

        return User.toDtoSet(users);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @CacheEvict(
            value = "users",
            key = "#email"
    )
    @Transactional
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }
}
