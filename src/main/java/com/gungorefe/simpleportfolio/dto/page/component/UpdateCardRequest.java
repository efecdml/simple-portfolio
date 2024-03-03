package com.gungorefe.simpleportfolio.dto.page.component;

public record UpdateCardRequest(
        int id,
        String title,
        String text,
        int displayOrder
) {
}
