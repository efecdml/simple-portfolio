package com.gungorefe.simpleportfolio.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gungorefe.simpleportfolio.config.security.SecurityProperties;
import com.gungorefe.simpleportfolio.dto.security.CreateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.service.security.JwtService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(
        scripts = {"/security/user-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties properties;
    private Cookie cookie;

    @BeforeEach
    void init() {
        User user = new User();
        user.setEmail("current@email.com");
        String accessToken = jwtService.generateToken(
                new HashMap<>(),
                user
        );
        cookie = new Cookie(properties.accessTokenCookieName(), accessToken);
        cookie.setMaxAge(Duration.ofDays(30).toMillisPart());
        cookie.setPath("/");
    }

    @Test
    void givenCreateUserRequestWithInvalidPassword_whenValidationPasswords_shouldThrowException() throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                "example@email.com",
                "invalidpassword",
                "invalidpassword",
                RoleName.ROLE_MOD
        );

        mvc.perform(post(("/api/users/competent"))
                        .cookie(cookie)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().reason("Invalid request content."))
                .andReturn();
    }

    @Test
    void givenCreateUserRequestForExistingUser_whenValidatingEmail_shouldThrowException() throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                "current@email.com",
                "passWord21",
                "passWord21",
                RoleName.ROLE_MOD
        );

        mvc.perform(post("/api/users/competent")
                        .cookie(cookie)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }
}
