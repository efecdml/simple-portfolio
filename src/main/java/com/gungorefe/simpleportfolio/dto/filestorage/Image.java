package com.gungorefe.simpleportfolio.dto.filestorage;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Objects;

public record Image(
        String name,
        MediaType mediaType,
        byte[] content
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(name, image.name) && Objects.equals(mediaType, image.mediaType) && Arrays.equals(content, image.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, mediaType);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", mediaType=" + mediaType +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
