package com.gungorefe.simpleportfolio.page.contact;

import com.gungorefe.simpleportfolio.dto.page.contact.UpdateContactRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.contact.Contact;
import com.gungorefe.simpleportfolio.service.page.contact.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(
        scripts = {"/page/contact/contact-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ContactServiceTest {
    @Autowired
    private ContactService service;

    @Test
    void givenLocaleName_whenFindingContactPage_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.getDto(LocaleName.TURKISH));
    }

    @Test
    void givenUpdateContactRequest_shouldUpdate() {
        UpdateContactRequest request = new UpdateContactRequest(
                "updated title",
                "updated text",
                "updated email",
                "updated location",
                "updated working days",
                "updated working hours",
                "updated gmaps"
        );
        Contact updated = service.update(
                LocaleName.TURKISH,
                request
        );

        assertAll(
                () -> assertEquals(request.title(), updated.getTitle()),
                () -> assertEquals(request.text(), updated.getText()),
                () -> assertEquals(request.email(), updated.getEmail()),
                () -> assertEquals(request.location(), updated.getLocation()),
                () -> assertEquals(request.workingDays(), updated.getWorkingDays()),
                () -> assertEquals(request.workingHours(), updated.getWorkingHours()),
                () -> assertEquals(request.googleMapsCoordination(), updated.getGoogleMapsCoordination())
        );
    }
}
