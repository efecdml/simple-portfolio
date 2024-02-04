package com.gungorefe.simpleportfolio.filestorage;

import com.gungorefe.simpleportfolio.config.filestorage.ImageServiceProperties;
import com.gungorefe.simpleportfolio.exception.filestorage.UnacceptableImageMimeTypeException;
import com.gungorefe.simpleportfolio.service.filestorage.ImageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ImageServiceTest {
    @Autowired
    private ImageService service;
    @Autowired
    private ImageServiceProperties properties;
    private final byte[] imageContent = {-1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, -1, -37, 0, 67, 0, 8, 6, 6, 7, 6, 5, 8, 7, 7, 7, 9, 9, 8, 10, 12, 20, 13, 12, 11, 11, 12, 25, 18, 19, 15, 20, 29, 26, 31, 30, 29, 26, 28, 28, 32, 36, 46, 39, 32, 34, 44, 35, 28, 28, 40, 55, 41, 44, 48, 49, 52, 52, 52, 31, 39, 57, 61, 56, 50, 60, 46, 51, 52, 50, -1, -37, 0, 67, 1, 9, 9, 9, 12, 11, 12, 24, 13, 13, 24, 50, 33, 28, 33, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, -1, -64, 0, 17, 8, 0, 8, 0, 8, 3, 1, 34, 0, 2, 17, 1, 3, 17, 1, -1, -60, 0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95, 8, 35, 66, -79, -63, 21, 82, -47, -16, 36, 51, 98, 114, -126, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -60, 0, 31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66, -111, -95, -79, -63, 9, 35, 51, 82, -16, 21, 98, 114, -47, 10, 22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23, -22, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -38, 0, 12, 3, 1, 0, 2, 17, 3, 17, 0, 63, 0, 110, -105, 97, 22, -123, 111, 120, -62, -22, 107, -127, 44, -88, 21, 76, 123, 6, 0, 111, -104, -14, 121, -25, -7, -47, 69, 21, -18, -48, -54, 112, -114, 26, -57, -15, 103, -107, -60, 49, -10, -40, -7, 84, -87, -85, 105, 126, 71, -1, -39};

    @Test
    void givenMultipartFileWithCurrentImageName_shouldCreateNewAndRemoveCurrent_thenReturnNewName() {
        String imageNameInDisk = createExampleImageFile();
        MultipartFile image = new MockMultipartFile(
                "image",
                "my image.png",
                MediaType.IMAGE_PNG_VALUE,
                imageContent
        );
        String newName = service.save(
                image,
                imageNameInDisk
        );
        Path pathOfNewImage = generatePath(newName);
        Path pathOfOldImage = generatePath(imageNameInDisk);

        assertAll(
                () -> assertFalse(newName.isBlank()),
                () -> assertTrue(Files.exists(pathOfNewImage)),
                () -> assertFalse(Files.exists(pathOfOldImage))
        );
    }

    @Test
    void givenMultipartFileWithWrongMimeType_whenValidatingMimeType_shouldThrowUnacceptableImageMimeTypeException() {
        byte[] content = "a text file".getBytes();
        MultipartFile text = new MockMultipartFile(
                "image",
                "my text file.png",
                MediaType.IMAGE_PNG_VALUE,
                content
        );

        assertThrowsExactly(
                UnacceptableImageMimeTypeException.class,
                () -> service.save(text)
        );
    }

    @Test
    void givenImageName_whenServingImageFile_shouldNotThrowAnyException() {
        String imageName = createExampleImageFile();

        assertDoesNotThrow(() -> service.get(imageName));
    }

    @AfterAll
    public void clearDirectory() throws IOException {
        Files.walk(properties.path())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private String createExampleImageFile() {
        String imageName = "my image.jpg";

        Path path = generatePath(imageName);
        InputStream stream = new ByteArrayInputStream(imageContent);

        if (!Files.exists(path)) {
            try {
                Files.copy(
                        stream,
                        path
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return imageName;
    }

    private Path generatePath(String name) {
        String s = String.join(
                "/",
                properties.path().toString(),
                name
        );

        return Paths.get(s);
    }
}
