package com.gungorefe.simpleportfolio.page.home;

import com.gungorefe.simpleportfolio.dto.page.home.UpdateHomeRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import com.gungorefe.simpleportfolio.service.page.home.HomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(
        scripts = {"/page/home/home-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class HomeServiceTest {
    @Autowired
    private HomeService service;

    @Test
    void givenLocaleName_whenFindingHomePage_shouldNotThrowAnyException() {
        assertDoesNotThrow(() -> service.getDto(LocaleName.TURKISH));
    }

    @Test
    void givenUpdateHomeRequest_shouldUpdate() {
        UpdateHomeRequest request = new UpdateHomeRequest(
                "updated title",
                "updated text",
                "updated second title",
                "updated second text"
        );
        Home home = service.update(
                LocaleName.ENGLISH,
                request
        );

        assertAll(
                () -> assertEquals(request.secondText(), home.getSecondText()),
                () -> assertEquals(request.title(), home.getTitle()),
                () -> assertEquals(request.text(), home.getText()),
                () -> assertEquals(request.secondTitle(), home.getSecondTitle())
        );
    }
}
