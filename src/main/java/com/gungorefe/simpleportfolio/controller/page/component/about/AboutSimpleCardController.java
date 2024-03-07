package com.gungorefe.simpleportfolio.controller.page.component.about;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.component.CardDto;
import com.gungorefe.simpleportfolio.dto.page.component.CreateCardRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdateCardRequest;
import com.gungorefe.simpleportfolio.service.page.component.about.AboutSimpleCardService;
import com.gungorefe.simpleportfolio.util.WebUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/pages/about/components/simple-card")
@RestController
public class AboutSimpleCardController {
    private final AboutSimpleCardService service;

    @Operation(summary = "Create About Simple Card component")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "/competent"
    )
    public ResponseEntity<Void> create(
            @RequestPart MultipartFile image,
            @RequestPart CreateCardRequest request
    ) {
        service.create(
                image,
                request
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get About Simple Card component")
    @GetMapping("/id/{id}")
    public ResponseEntity<CardDto> getDto(@PathVariable int id) {
        CardDto dto = service.getDto(id);

        return WebUtils.responseEntityForCachingDto(dto);
    }

    @Operation(summary = "Get all About Simple Card components")
    @GetMapping("/competent/page-id/{pageId}")
    public ResponseEntity<List<CardDto>> getAllDtos(@PathVariable int pageId) {
        List<CardDto> dtos = service.getAllDtos(pageId);

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get image of About Simple Card component")
    @GetMapping("/id/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Image image = service.getImage(id);

        return WebUtils.responseEntityForCachingImage(image);
    }

    @Operation(summary = "Update About Simple Card component")
    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "/competent"
    )
    public ResponseEntity<Void> update(
            @RequestPart(required = false) MultipartFile image,
            @RequestPart UpdateCardRequest request
    ) {
        service.update(
                request,
                image
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete About Simple Card component")
    @DeleteMapping("/competent/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
