package com.gungorefe.simpleportfolio.service.filestorage;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
public abstract class FileStorageService {
    public abstract void init();

    protected void store(
            Path path,
            InputStream stream
    ) {
        try {
            Files.copy(
                    stream,
                    path
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected byte[] serve(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void remove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
