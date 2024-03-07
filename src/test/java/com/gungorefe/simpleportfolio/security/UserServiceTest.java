package com.gungorefe.simpleportfolio.security;

import com.gungorefe.simpleportfolio.dto.security.CreateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.RoleName;
import com.gungorefe.simpleportfolio.dto.security.UpdateUserRequest;
import com.gungorefe.simpleportfolio.entity.security.User;
import com.gungorefe.simpleportfolio.service.security.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(
        scripts = {"/security/user-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    void givenCreateUserRequest_shouldCreate() {
        CreateUserRequest request = new CreateUserRequest(
                "example@email.com",
                "aValidPassword213",
                "aValidPassword213",
                RoleName.ROLE_MOD
        );
        User user = service.create(request);

        assertAll(
                () -> assertEquals(request.email(), user.getEmail()),
                () -> assertNotEquals(request.password(), user.getPassword()),
                () -> assertEquals(request.roleName(), user.getRole().getName())
        );
    }

    @Test
    void givenUpdateUserRequest_shouldUpdate() {
        UpdateUserRequest request = new UpdateUserRequest(
                "current@email.com",
                "updated@email.com",
                "newPassword213",
                "newPassword213",
                RoleName.ROLE_ADMIN
        );
        User user = service.update(request);

        assertAll(
                () -> assertEquals(request.newEmail(), user.getEmail()),
                () -> assertNotEquals(request.password(), user.getPassword())
        );
    }

    @Test
    void givenUserEmailAddress_whenDeleting_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.delete("current@email.com"));
    }
}
