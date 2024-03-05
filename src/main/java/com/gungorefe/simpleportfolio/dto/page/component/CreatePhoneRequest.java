package com.gungorefe.simpleportfolio.dto.page.component;

public record CreatePhoneRequest(
        int pageId,
        String tag,
        String number,
        int displayOrder
) {
}
