package com.gungorefe.simpleportfolio.controller.page.home;

import com.gungorefe.simpleportfolio.annotation.web.AcceptHeader;
import com.gungorefe.simpleportfolio.dto.page.home.HomeDto;
import com.gungorefe.simpleportfolio.dto.page.home.UpdateHomeRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.service.page.home.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/pages/home")
@RestController
public class HomeController {
    private final HomeService service;

    @Operation(summary = "Get Home page")
    @GetMapping
    public ResponseEntity<HomeDto> getDto(@AcceptHeader LocaleName localeName) {
        HomeDto dto = service.getDto(localeName);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Update Home page")
    @PutMapping("/competent/**")
    public ResponseEntity<Void> update(
            @AcceptHeader LocaleName localeName,
            @RequestBody UpdateHomeRequest request
    ) {
        service.update(
                localeName,
                request
        );

        return ResponseEntity.ok().build();
    }
}
