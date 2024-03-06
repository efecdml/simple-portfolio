package com.gungorefe.simpleportfolio.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@ConfigurationPropertiesScan
@EnableAsync
@Configuration
public class AppConfig {
}
