package com.gungorefe.simpleportfolio.dto.converter.page;

import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class LocaleNameConverter implements Converter<String, LocaleName> {
    @Override
    public LocaleName convert(@NonNull String source) {
        return LocaleName.get(source)
                .orElseThrow(() -> ExceptionFactory.localeNotFoundException(source));
    }
}
