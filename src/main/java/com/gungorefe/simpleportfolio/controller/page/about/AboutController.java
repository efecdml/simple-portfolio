package com.gungorefe.simpleportfolio.controller.page.about;

import com.gungorefe.simpleportfolio.annotation.web.AcceptHeader;
import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.about.AboutDto;
import com.gungorefe.simpleportfolio.dto.page.about.UpdateAboutRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.service.page.about.AboutService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/pages/about")
@RestController
public class AboutController {
    private final AboutService service;

    @Operation(summary = "Get About page")
    @GetMapping
    public ResponseEntity<AboutDto> getDto(@AcceptHeader LocaleName localeName) {
        AboutDto dto = service.getDto(localeName);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Get image of About page")
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@AcceptHeader LocaleName localeName) {
        Image image = service.getImage(localeName);

        return ResponseEntity.ok()
                .contentType(image.mediaType())
                .body(image.content());
    }

    @Operation(summary = "Update About page")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(
            @AcceptHeader LocaleName localeName,
            @RequestPart(required = false) MultipartFile image,
            @RequestPart UpdateAboutRequest request
    ) {
        service.update(
                localeName,
                request,
                image
        );

        return ResponseEntity.ok().build();
    }
}
