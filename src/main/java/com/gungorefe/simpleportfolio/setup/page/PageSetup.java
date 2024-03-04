package com.gungorefe.simpleportfolio.setup.page;

import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.about.About;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import com.gungorefe.simpleportfolio.repository.page.LocaleRepository;
import com.gungorefe.simpleportfolio.repository.page.about.AboutRepository;
import com.gungorefe.simpleportfolio.repository.page.home.HomeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Log4j2
@Profile("!test")
@Component
public class PageSetup implements ApplicationListener<ContextRefreshedEvent> {
    private final LocaleRepository localeRepository;
    private final HomeRepository homeRepository;
    private final AboutRepository aboutRepository;
    private final LocaleName localeNameEnglish = LocaleName.ENGLISH;
    private final LocaleName localeNameTurkish = LocaleName.TURKISH;
    private Locale localeEnglish;
    private Locale localeTurkish;

    private void insertLocalesIfNotExist() {
        if (!localeRepository.existsByName(localeNameEnglish)) {
            log.warn("English locale not found, inserting..");
            localeEnglish = localeRepository.save(new Locale(localeNameEnglish));
        }
        if (!localeRepository.existsByName(localeNameTurkish)) {
            log.warn("Turkish locale not found, inserting..");
            localeTurkish = localeRepository.save(new Locale(localeNameTurkish));
        }
    }

    private void insertHomePagesIfNotExist() {
        if (!homeRepository.existsByLocale_Name(localeNameEnglish)) {
            log.warn("English Home page not found, inserting..");
            homeRepository.save(new Home(localeEnglish, "", "", "", ""));
        }
        if (!homeRepository.existsByLocale_Name(localeNameTurkish)) {
            log.warn("Turkish Home page not found, inserting..");
            homeRepository.save(new Home(localeTurkish, "", "", "", ""));
        }
    }

    private void insertAboutPagesIfNotExist() {
        if (!aboutRepository.existsByLocale_Name(localeNameEnglish)) {
            log.warn("English About page not found, inserting..");
            aboutRepository.save(new About(localeEnglish, "", "", ""));
        }
        if (!aboutRepository.existsByLocale_Name(localeNameTurkish)) {
            log.warn("Turkish About page not found, inserting..");
            aboutRepository.save(new About(localeTurkish, "", "", ""));
        }
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        insertLocalesIfNotExist();
        insertHomePagesIfNotExist();
        insertAboutPagesIfNotExist();
    }
}
