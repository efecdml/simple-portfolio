package com.gungorefe.simpleportfolio.config.filestorage;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageServiceConfig {
    @Bean
    public Tika tika() {
        return new Tika();
    }
}
