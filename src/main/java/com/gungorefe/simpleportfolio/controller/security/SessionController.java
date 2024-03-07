package com.gungorefe.simpleportfolio.controller.security;

import com.gungorefe.simpleportfolio.dto.security.LoginRequest;
import com.gungorefe.simpleportfolio.dto.security.LoginResponse;
import com.gungorefe.simpleportfolio.dto.security.UpdatePasswordRequest;
import com.gungorefe.simpleportfolio.service.security.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/session")
@RestController
public class SessionController {
    private final SessionService service;

    @Operation(summary = "Login")
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        LoginResponse response = service.login(request);
        String cookie = response.cookie().toString();
        String roleName = response.roleName().name();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookie
                )
                .body(roleName);
    }

    @Operation(summary = "Logout")
    @GetMapping
    public ResponseEntity<Void> logout() {
        String cookie = service.logout().toString();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookie
                )
                .build();
    }

    @Operation(summary = "Get Password Reset Token by email")
    @GetMapping("/password-reset/email/{email}")
    public ResponseEntity<Void> createPasswordResetToken(@PathVariable String email) {
        service.createAndSendPasswordResetToken(email);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update password with Password Reset Token")
    @PutMapping("/password-reset/token/{token}")
    public ResponseEntity<Void> resetPassword(
            @PathVariable UUID token,
            @RequestBody @Valid UpdatePasswordRequest request
    ) {
        service.resetPassword(
                token,
                request
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        return ResponseEntity.ok().build();
    }
}
