package com.gungorefe.simpleportfolio.dto.page.component;

public record UpdatePhoneRequest(
        int id,
        String tag,
        String number,
        int displayOrder
) {
}
