package com.gungorefe.simpleportfolio.exception;

import com.gungorefe.simpleportfolio.exception.filestorage.MalformedImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageNameException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
