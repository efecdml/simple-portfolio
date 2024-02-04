package com.gungorefe.simpleportfolio.config.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "storage.image")
public record ImageServiceProperties(Path path) {
}
