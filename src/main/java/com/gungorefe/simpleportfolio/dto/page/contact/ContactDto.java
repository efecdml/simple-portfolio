package com.gungorefe.simpleportfolio.dto.page.contact;

public record ContactDto(
        String title,
        String text,
        String email,
        String location,
        String workingDays,
        String workingHours,
        String googleMapsCoordination
) {
}
