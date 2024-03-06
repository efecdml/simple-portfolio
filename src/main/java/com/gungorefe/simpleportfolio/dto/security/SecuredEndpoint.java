package com.gungorefe.simpleportfolio.dto.security;

import java.util.Arrays;

public enum SecuredEndpoint {
    ABOUT("/api/pages/about/competent/**"),
    CONTACT("/api/pages/contact/competent/**"),
    HOME("/api/pages/home/competent/**"),
    ABOUT_SIMPLE_CARD("/api/pages/about/components/simple-card/competent/**"),
    CONTACT_PHONE("/api/pages/contact/components/phone/competent/**"),
    HOME_CAROUSEL_SECTION("/api/pages/home/components/carousel-section/competent/**"),
    HOME_DETAILED_CARD("/api/pages/home/components/detailed-card/competent/**");
    public final String value;

    SecuredEndpoint(String value) {
        this.value = value;
    }

    private static final String[] VALUES = Arrays.stream(values())
            .map(v -> v.value)
            .toArray(String[]::new);

    public static String[] getValues() {
        return VALUES;
    }
}
