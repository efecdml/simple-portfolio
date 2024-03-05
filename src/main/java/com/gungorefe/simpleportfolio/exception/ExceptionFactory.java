package com.gungorefe.simpleportfolio.exception;

import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.exception.filestorage.MalformedImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageNameException;
import com.gungorefe.simpleportfolio.exception.page.LocaleNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.PageNotFoundException;
import com.gungorefe.simpleportfolio.exception.page.component.ComponentNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionFactory {
    public static MalformedImageMimeTypeException malformedImageMimeTypeException(String mimeType) {
        return new MalformedImageMimeTypeException("Image could not be processed due to unsupported MIME type: " + mimeType);
    }

    public static UnacceptableImageNameException unacceptableImageNameException(String imageName) {
        return new UnacceptableImageNameException("Unacceptable image name: " + imageName);
    }

    public static UnacceptableImageMimeTypeException unacceptableImageMimeTypeException(String mimeType) {
        return new UnacceptableImageMimeTypeException("Unacceptable MIME type: " + mimeType);
    }

    public static PageNotFoundException pageNotFoundException(
            String pageName,
            LocaleName localeName
    ) {
        return new PageNotFoundException(MessageFormat.format(
                "Page: {0} not found by locale: {1}",
                pageName,
                localeName
        ));
    }

    public static LocaleNotFoundException localeNotFoundException(String localeName) {
        return new LocaleNotFoundException("Locale not found: " + localeName);
    }

    public static ComponentNotFoundException componentNotFoundException(
            String componentName,
            int id
    ) {
        return new ComponentNotFoundException(MessageFormat.format(
                "Component: {0} not found by ID: {1}",
                componentName,
                id
        ));
    }
}
