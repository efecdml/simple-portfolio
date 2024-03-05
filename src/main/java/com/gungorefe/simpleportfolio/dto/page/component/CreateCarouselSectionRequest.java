package com.gungorefe.simpleportfolio.dto.page.component;

public record CreateCarouselSectionRequest(
        int pageId,
        String text,
        int displayOrder
) {
}
