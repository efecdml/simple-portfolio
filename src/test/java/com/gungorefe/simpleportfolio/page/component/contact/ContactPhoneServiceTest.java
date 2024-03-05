package com.gungorefe.simpleportfolio.page.component.contact;

import com.gungorefe.simpleportfolio.dto.page.component.CreatePhoneRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdatePhoneRequest;
import com.gungorefe.simpleportfolio.entity.page.component.contact.ContactPhone;
import com.gungorefe.simpleportfolio.service.page.component.contact.ContactPhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(
        scripts = {"/page/component/contact/contact-phone-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ContactPhoneServiceTest {
    @Autowired
    private ContactPhoneService service;

    @Test
    void givenCreatePhoneRequest_shouldCreate() {
        CreatePhoneRequest request = new CreatePhoneRequest(
                1,
                "created tag",
                "created number",
                23
        );
        ContactPhone created = service.create(request);

        assertAll(
                () -> assertEquals(request.tag(), created.getTag()),
                () -> assertEquals(request.number(), created.getNumber()),
                () -> assertEquals(request.displayOrder(), created.getDisplayOrder())
        );
    }

    @Test
    void givenId_whenFindingDto_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.getDto(2));
    }

    @Test
    void givenContactId_whenFindingAllDtos_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.getAllDtos(1));
    }

    @Test
    void givenUpdatePhoneRequest_shouldUpdate() {
        UpdatePhoneRequest request = new UpdatePhoneRequest(
                1,
                "updated tag",
                "updated numbber",
                3
        );
        ContactPhone updated = service.update(request);

        assertAll(
                () -> assertEquals(request.tag(), updated.getTag()),
                () -> assertEquals(request.number(), updated.getNumber()),
                () -> assertEquals(request.displayOrder(), updated.getDisplayOrder())
        );
    }

    @Test
    void givenId_whenDeleting_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.delete(2));
    }
}
