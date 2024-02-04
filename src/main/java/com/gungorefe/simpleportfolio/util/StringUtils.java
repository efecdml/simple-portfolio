package com.gungorefe.simpleportfolio.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {
    public static String appendDateTime(String s) {
        String dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS).toString();

        return String.join(
                "-",
                s,
                dateTime
        );
    }

    public static String regulate(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFKD)
                .replaceAll("(\\p{M})|(\\p{P})| ($)", "") // Removes mark characters and punctuations
                .replace(" ", "-")
                .toLowerCase();
    }
}
