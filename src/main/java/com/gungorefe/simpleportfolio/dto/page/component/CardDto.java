package com.gungorefe.simpleportfolio.dto.page.component;

public record CardDto(
        int id,
        String imageName,
        String title,
        String text,
        int displayOrder
) {
}
