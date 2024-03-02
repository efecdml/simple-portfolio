package com.gungorefe.simpleportfolio.entity.page;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum LocaleName {
    ENGLISH("en"),
    TURKISH("tr");
    public final String value;

    LocaleName(String value) {
        this.value = value;
    }

    private static final Map<String, LocaleName> VALUES = new HashMap<>();

    static {
        for (LocaleName n : values()) {
            VALUES.put(n.value, n);
        }
    }

    public static Optional<LocaleName> get(String name) {
        return Optional.ofNullable(VALUES.get(name));
    }
}
