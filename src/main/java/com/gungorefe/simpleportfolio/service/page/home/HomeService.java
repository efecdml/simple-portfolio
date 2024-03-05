package com.gungorefe.simpleportfolio.service.page.home;

import com.gungorefe.simpleportfolio.dto.page.home.HomeDto;
import com.gungorefe.simpleportfolio.dto.page.home.UpdateHomeRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.home.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final HomeRepository repository;

    public HomeDto getDto(LocaleName localeName) {
        return repository.findWithCarouselSectionsByLocale_Name(localeName)
                .map(Home::toDto)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(Home.NAME, localeName));
    }

    public Home update(
            LocaleName localeName,
            UpdateHomeRequest request
    ) {
        Home home = repository.findForUpdateByLocale_Name(localeName)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(Home.NAME, localeName));
        home = applyChanges(
                home,
                request
        );

        return repository.save(home);
    }

    private Home applyChanges(
            Home home,
            UpdateHomeRequest request
    ) {
        home.setTitle(request.title());
        home.setText(request.text());
        home.setSecondTitle(request.secondTitle());
        home.setSecondText(request.secondText());

        return home;
    }
}
