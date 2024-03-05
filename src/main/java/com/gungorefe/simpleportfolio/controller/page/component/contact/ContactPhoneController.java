package com.gungorefe.simpleportfolio.controller.page.component.contact;

import com.gungorefe.simpleportfolio.dto.page.component.CreatePhoneRequest;
import com.gungorefe.simpleportfolio.dto.page.component.PhoneDto;
import com.gungorefe.simpleportfolio.dto.page.component.UpdatePhoneRequest;
import com.gungorefe.simpleportfolio.service.page.component.contact.ContactPhoneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/pages/contact/components/phone")
@RestController
public class ContactPhoneController {
    private final ContactPhoneService service;

    @Operation(summary = "Create Contact Phone component")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePhoneRequest request) {
        service.create(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get Contact Phone component")
    public ResponseEntity<PhoneDto> getDto(@PathVariable int id) {
        PhoneDto dto = service.getDto(id);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Get all Contact Phone components")
    @GetMapping("/page-id/{pageId}")
    public ResponseEntity<List<PhoneDto>> getAllDtos(@PathVariable int pageId) {
        List<PhoneDto> dtos = service.getAllDtos(pageId);

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Update Contact Phone component")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UpdatePhoneRequest request) {
        service.update(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete Contact Phone component")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
