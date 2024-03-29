package com.gungorefe.simpleportfolio.controller.page.contact;

import com.gungorefe.simpleportfolio.annotation.web.AcceptHeader;
import com.gungorefe.simpleportfolio.dto.page.contact.ContactDto;
import com.gungorefe.simpleportfolio.dto.page.contact.UpdateContactRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.service.page.contact.ContactService;
import com.gungorefe.simpleportfolio.util.WebUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/pages/contact")
@RestController
public class ContactController {
    private final ContactService service;

    @Operation(summary = "Get Contact page")
    @GetMapping
    public ResponseEntity<ContactDto> getDto(@AcceptHeader LocaleName localeName) {
        ContactDto dto = service.getDto(localeName);

        return WebUtils.responseEntityForCachingDto(dto);
    }

    @Operation(summary = "Update Contact page")
    @PutMapping("/competent")
    public ResponseEntity<Void> update(
            @AcceptHeader LocaleName localeName,
            @RequestBody UpdateContactRequest request
    ) {
        service.update(
                localeName,
                request
        );

        return ResponseEntity.ok().build();
    }
}
