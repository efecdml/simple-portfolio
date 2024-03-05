package com.gungorefe.simpleportfolio.dto.page.home;

import com.gungorefe.simpleportfolio.dto.page.component.CarouselSectionDto;

import java.util.List;

public record HomeDto(
        int id,
        String title,
        String text,
        String secondTitle,
        String secondText,
        List<CarouselSectionDto> carouselSections
) {
}
