package com.gungorefe.simpleportfolio.service.page.about;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.about.AboutDto;
import com.gungorefe.simpleportfolio.dto.page.about.UpdateAboutRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.about.About;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.about.AboutRepository;
import com.gungorefe.simpleportfolio.service.filestorage.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AboutService {
    private final AboutRepository repository;
    private final ImageService imageService;

    public AboutDto getDto(LocaleName localeName) {
        return repository.findByLocale_Name(localeName)
                .map(About::toDto)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(About.NAME, localeName));
    }

    public Image getImage(LocaleName localeName) {
        String imageName = repository.findImageNameByLocale_Name(localeName)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(About.NAME, localeName));

        return imageService.get(imageName);
    }

    public About update(
            LocaleName localeName,
            UpdateAboutRequest request,
            @Nullable MultipartFile image
    ) {
        About about = repository.findForUpdateByLocale_Name(localeName)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(About.NAME, localeName));
        String imageName = saveImageIfNotNull(
                about.getImageName(),
                image
        );
        about = applyChanges(
                about,
                imageName,
                request
        );

        return repository.save(about);
    }

    private String saveImageIfNotNull(
            String currentImageName,
            MultipartFile image
    ) {
        return image == null
                ? currentImageName
                : imageService.save(image, currentImageName);
    }

    private About applyChanges(
            About about,
            String imageName,
            UpdateAboutRequest request
    ) {
        about.setImageName(imageName);
        about.setTitle(request.title());
        about.setText(request.text());

        return about;
    }
}
