package com.gungorefe.simpleportfolio.util;

import com.google.common.net.HttpHeaders;
import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

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

    public static String getIp(HttpServletRequest request) {
        String xffHeader = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        String remoteAddress = request.getRemoteAddr();

        return xffHeader == null ||
                xffHeader.isBlank() ||
                !xffHeader.contains(remoteAddress)
                ? remoteAddress
                : xffHeader.split(";")[0];
    }

    public static <T> ResponseEntity<T> responseEntityForCachingDto(T body) {
        return ResponseEntity.ok()
                .eTag(String.valueOf(body.hashCode()))
                .cacheControl(CacheControl.maxAge(Duration.ZERO))
                .body(body);
    }

    public static ResponseEntity<byte[]> responseEntityForCachingImage(Image image) {
        return ResponseEntity.ok()
                .eTag(image.name())
                .cacheControl(CacheControl.maxAge(Duration.ZERO))
                .contentType(image.mediaType())
                .body(image.content());
    }
}
