package com.gungorefe.simpleportfolio.controller.page.component.home;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.component.CarouselSectionDto;
import com.gungorefe.simpleportfolio.dto.page.component.CreateCarouselSectionRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdateCarouselSectionRequest;
import com.gungorefe.simpleportfolio.service.page.component.home.HomeCarouselSectionService;
import com.gungorefe.simpleportfolio.util.WebUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/pages/home/components/carousel-section")
@RestController
public class HomeCarouselSectionController {
    private final HomeCarouselSectionService service;

    @Operation(summary = "Create Home Carousel Section component")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "/competent"
    )
    public ResponseEntity<Void> create(
            @RequestPart MultipartFile image,
            @RequestPart CreateCarouselSectionRequest request
    ) {
        service.create(
                image,
                request
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Home Carousel Section component")
    @GetMapping("/id/{id}")
    public ResponseEntity<CarouselSectionDto> getDto(@PathVariable int id) {
        CarouselSectionDto dto = service.getDto(id);

        return WebUtils.responseEntityForCachingDto(dto);
    }

    @Operation(summary = "Get all Home Carousel Section components")
    @GetMapping("/competent/page-id/{pageId}")
    public ResponseEntity<List<CarouselSectionDto>> getAllDtos(@PathVariable int pageId) {
        List<CarouselSectionDto> dtos = service.getAllDtos(pageId);

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get image of Home Carousel Section component")
    @GetMapping("/id/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Image image = service.getImage(id);

        return WebUtils.responseEntityForCachingImage(image);
    }

    @Operation(summary = "Update Home Carousel Section component")
    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "/competent"
    )
    public ResponseEntity<Void> update(
            @RequestPart(required = false) MultipartFile image,
            @RequestPart UpdateCarouselSectionRequest request
    ) {
        service.update(
                request,
                image
        );

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete Home Carousel Section component")
    @DeleteMapping("/competent/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
