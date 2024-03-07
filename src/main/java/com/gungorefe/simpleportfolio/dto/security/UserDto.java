package com.gungorefe.simpleportfolio.dto.security;

public record UserDto(
        String email,
        RoleName roleName
) {
}
