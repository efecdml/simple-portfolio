package com.gungorefe.simpleportfolio.dto.filestorage;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ImageMediaType {
    IMAGE_SVG_XML("image/svg+xml", new MediaType("image", "svg+xml")),
    WEBP("image/webp", new MediaType("image", "webp")),
    GIF("image/gif", MediaType.IMAGE_GIF),
    JPEG("image/jpeg", MediaType.IMAGE_JPEG),
    PNG("image/png", MediaType.IMAGE_PNG);
    public final String label;
    public final MediaType value;
    private static final Map<String, MediaType> VALUES = new HashMap<>();

    static {
        for (ImageMediaType i : values()) {
            VALUES.put(i.label, i.value);
        }
    }

    ImageMediaType(String label, MediaType value) {
        this.label = label;
        this.value = value;
    }

    public static Optional<MediaType> get(String label) {
        return Optional.ofNullable(VALUES.get(label));
    }

    public static boolean contains(String label) {
        return VALUES.containsKey(label);
    }
}
