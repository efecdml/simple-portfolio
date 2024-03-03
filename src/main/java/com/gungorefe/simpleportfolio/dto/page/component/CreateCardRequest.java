package com.gungorefe.simpleportfolio.dto.page.component;

public record CreateCardRequest(
        int pageId,
        String title,
        String text,
        int displayOrder
) {
}
