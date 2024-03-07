package com.gungorefe.simpleportfolio.service.security;

import com.gungorefe.simpleportfolio.config.security.SecurityProperties;
import com.gungorefe.simpleportfolio.dto.security.CreateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.UpdateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.UserDto;
import com.gungorefe.simpleportfolio.entity.security.Role;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final HttpServletRequest request;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final String accessTokenCookieName;

    public UserService(HttpServletRequest request, JwtService jwtService, PasswordEncoder passwordEncoder, SecurityService securityService, SecurityProperties securityProperties) {
        this.request = request;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        accessTokenCookieName = securityProperties.accessTokenCookieName();
    }

    public User create(CreateUserRequest request) {
        String email = request.email();

        if (securityService.userExists(email))
            throw ExceptionFactory.emailAlreadyInUseException(email);

        Role role = securityService.getRole(request.roleName());
        User user = createEntity(
                request.email(),
                request.password(),
                role
        );

        return securityService.saveUser(user);
    }

    public UserDto getDto() {
        String accessToken = WebUtils.getCookieValue(
                request,
                accessTokenCookieName
        ).orElseThrow(ExceptionFactory::badCredentialsException);
        String email = jwtService.extractUsername(accessToken);
        User user = securityService.getUser(
                false,
                email
        );

        return user.toDto();
    }

    public Set<UserDto> getAllDtos() {
        return securityService.getAllUsers();
    }

    public User update(UpdateUserRequest request) {
        User currentUser = securityService.getUser(
                false,
                request.currentEmail()
        );
        String newEmail = request.newEmail();

        if (securityService.userExists(newEmail))
            throw ExceptionFactory.emailAlreadyInUseException(newEmail);

        User user = applyChanges(
                currentUser,
                newEmail,
                request.password()
        );

        return securityService.saveUser(user);
    }

    public void delete(String email) {
        securityService.delete(email);
    }

    private User createEntity(
            String email,
            String password,
            Role role
    ) {
        return new User(
                email,
                passwordEncoder.encode(password),
                role
        );
    }

    private User applyChanges(
            User user,
            String email,
            String password
    ) {
        return new User(
                user.getId(),
                email,
                passwordEncoder.encode(password),
                user.getRole()
        );
    }
}
