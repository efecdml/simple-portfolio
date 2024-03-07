package com.gungorefe.simpleportfolio.config.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "web")
public record WebProperties(List<String> domains) {
}
