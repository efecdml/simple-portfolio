package com.gungorefe.simpleportfolio.controller.security;

import com.gungorefe.simpleportfolio.dto.security.CreateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.UpdateUserRequest;
import com.gungorefe.simpleportfolio.dto.security.UserDto;
import com.gungorefe.simpleportfolio.service.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService service;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create user")
    @PostMapping("/competent")
    public ResponseEntity<Void> create(@RequestBody @Valid CreateUserRequest request) {
        service.create(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user by access token")
    @GetMapping("/competent")
    public ResponseEntity<UserDto> getDto() {
        UserDto dto = service.getDto();

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users")
    @GetMapping("/competent/all")
    public ResponseEntity<Set<UserDto>> getAllDtos() {
        Set<UserDto> dtos = service.getAllDtos();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user")
    @PutMapping("/competent")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateUserRequest request) {
        service.update(request);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user")
    @DeleteMapping("/competent/email/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        service.delete(email);

        return ResponseEntity.ok().build();
    }
}
