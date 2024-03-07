package com.gungorefe.simpleportfolio.config.web;

import com.gungorefe.simpleportfolio.annotation.web.AcceptHeaderArgumentResolver;
import com.gungorefe.simpleportfolio.dto.converter.page.LocaleNameConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ConversionService conversionService;

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(new LocaleNameConverter());
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AcceptHeaderArgumentResolver(conversionService));
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        Locale localeTurkish = new Locale.Builder()
                .setLanguage("tr")
                .build();
        resolver.setSupportedLocales(List.of(
                Locale.ENGLISH,
                localeTurkish
        ));
        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(Duration.ZERO));
    }
}
