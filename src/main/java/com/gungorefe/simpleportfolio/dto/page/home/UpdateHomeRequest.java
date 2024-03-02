package com.gungorefe.simpleportfolio.dto.page.home;

public record UpdateHomeRequest(
        String title,
        String text,
        String secondTitle,
        String secondText
) {
}
