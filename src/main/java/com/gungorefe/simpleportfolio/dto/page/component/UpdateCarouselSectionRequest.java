package com.gungorefe.simpleportfolio.dto.page.component;

public record UpdateCarouselSectionRequest(
        int id,
        String text,
        int displayOrder
) {
}
