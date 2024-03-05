package com.gungorefe.simpleportfolio.dto.page.about;

import com.gungorefe.simpleportfolio.dto.page.component.CardDto;

import java.util.List;

public record AboutDto(
        int id,
        String imageName,
        String title,
        String text,
        List<CardDto> simpleCards
) {
}
