package com.gungorefe.simpleportfolio.dto.page.contact;

import com.gungorefe.simpleportfolio.dto.page.component.PhoneDto;

import java.util.List;

public record ContactDto(
        String title,
        String text,
        String email,
        String location,
        String workingDays,
        String workingHours,
        String googleMapsCoordination,
        List<PhoneDto> phones
) {
}
