package com.gungorefe.simpleportfolio.setup;

import com.gungorefe.simpleportfolio.service.filestorage.ImageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppSetup implements ApplicationListener<ContextRefreshedEvent> {
    private final ImageService imageService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        imageService.init();
    }
}
