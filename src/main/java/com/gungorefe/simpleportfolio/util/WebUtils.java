package com.gungorefe.simpleportfolio.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebUtils extends org.springframework.web.util.WebUtils {
    public static ResponseCookie createCookie(
            String name,
            String value,
            Duration age
    ) {
        return ResponseCookie
                .from(name, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(age)
                .build();
    }

    public static Optional<String> getCookieValue(
            HttpServletRequest request,
            String intendedName
    ) {
        Cookie[] cookies = request.getCookies();

        return Stream.ofNullable(cookies)
                .flatMap(Arrays::stream)
                .filter(c -> {
                    String actualName = c.getName();

                    return intendedName.equals(actualName);
                })
                .map(Cookie::getValue)
                .findFirst();
    }

    public static ResponseCookie deleteCookie(String name) {
        return createCookie(
                name,
                "",
                Duration.ZERO
        );
    }
}
