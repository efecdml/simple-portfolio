package com.gungorefe.simpleportfolio.service.filestorage;

import com.gungorefe.simpleportfolio.config.filestorage.ImageServiceProperties;
import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.filestorage.ImageMediaType;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Log4j2
@Service
public class ImageService extends FileStorageService {
    private final Path path;
    private final Tika tika;

    public ImageService(ImageServiceProperties properties, Tika tika) {
        this.path = properties.path();
        this.tika = tika;
    }

    public String save(MultipartFile image) {
        return store(image);
    }

    public String save(
            MultipartFile image,
            String currentImageName
    ) {
        if (!currentImageName.isBlank()) {
            Path currentPath = generatePath(currentImageName);

            remove(currentPath);
        } else
            log.warn("The current image name is empty. Ignore this warning if database has just been created.");

        return store(image);
    }

    public Image get(String name) {
        byte[] content = serve(generatePath(name));
        MediaType mediaType = generateMediaType(content);

        return new Image(
                mediaType,
                content
        );
    }

    public void delete(String name) {
        Path path = generatePath(name);

        if (Files.exists(path))
            remove(path);
        else
            log.warn("File not exists: " + name);
    }

    private String store(MultipartFile image) {
        String currentName = Optional.ofNullable(image.getOriginalFilename())
                .orElse("image");
        String newName;
        Path path;
        InputStream inputStream;

        validateNameLength(currentName);
        validateMimeType(image);

        newName = generateNewName(currentName);
        path = generatePath(newName);
        inputStream = getInputStream(image);

        super.store(
                path,
                inputStream
        );

        return newName;
    }

    private void validateNameLength(String name) {
        int length = name.length();

        if (length < 5 ||
                length > 226)
            throw ExceptionFactory.unacceptableImageNameException(name);
    }

    private void validateMimeType(MultipartFile image) {
        byte[] bytes;
        String mimeType;

        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        mimeType = tika.detect(bytes);

        boolean unaccepted = !ImageMediaType.contains(mimeType);

        if (unaccepted)
            throw ExceptionFactory.unacceptableImageMimeTypeException(mimeType);
    }


    private String generateNewName(String name) {
        String extension = FilenameUtils.getExtension(name);
        String nameWithoutExtension = removeExtension(name, extension);
        String regulatedName = StringUtils.regulate(nameWithoutExtension);
        String nameWithDateTime = StringUtils.appendDateTime(regulatedName);

        return String.join(
                ".",
                nameWithDateTime,
                extension
        );
    }

    private Path generatePath(String name) {
        String result = String.join(
                "/",
                path.toString(),
                name
        );

        return Paths.get(result);
    }

    private InputStream getInputStream(MultipartFile image) {
        try {
            return image.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private MediaType generateMediaType(byte[] bytes) {
        String mimeType = tika.detect(bytes);

        return ImageMediaType.get(mimeType)
                .orElseThrow(() -> ExceptionFactory.malformedImageMimeTypeException(mimeType));
    }

    private String removeExtension(
            String name,
            String extension
    ) {
        return name.substring(0, name.length() - extension.length());
    }

    @Override
    public void init() {
        if (!Files.exists(path)) {
            log.info("Image directory not found, creating: " + path);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
