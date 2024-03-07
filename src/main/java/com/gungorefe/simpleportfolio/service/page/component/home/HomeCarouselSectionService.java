package com.gungorefe.simpleportfolio.service.page.component.home;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.component.CarouselSectionDto;
import com.gungorefe.simpleportfolio.dto.page.component.CreateCarouselSectionRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdateCarouselSectionRequest;
import com.gungorefe.simpleportfolio.entity.page.component.CarouselSection;
import com.gungorefe.simpleportfolio.entity.page.component.home.HomeCarouselSection;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.component.home.HomeCarouselSectionRepository;
import com.gungorefe.simpleportfolio.service.filestorage.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeCarouselSectionService {
    private final ImageService imageService;
    private final HomeCarouselSectionRepository repository;

    public HomeCarouselSection create(
            MultipartFile image,
            CreateCarouselSectionRequest request
    ) {
        String imageName = imageService.save(image);
        HomeCarouselSection section = generateEntity(
                imageName,
                request
        );

        return repository.save(section);
    }

    @Cacheable(
            value = "homeCarouselSectionComponents",
            key = "#id"
    )
    public CarouselSectionDto getDto(int id) {
        return repository.findById(id)
                .map(HomeCarouselSection::toDto)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeCarouselSection.NAME, id));
    }

    public List<CarouselSectionDto> getAllDtos(int homeId) {
        List<HomeCarouselSection> sections = repository.findAllByHome_Id(homeId);

        return CarouselSection.toDtoList(sections);
    }

    @Cacheable(
            value = "homeCarouselSectionComponentImages",
            key = "#id"
    )
    public Image getImage(int id) {
        String imageName = getImageName(id);

        return imageService.get(imageName);
    }

    @Caching(evict = {
            @CacheEvict(
                    value = "homeCarouselSectionComponents",
                    key = "#request.id()"
            ),
            @CacheEvict(
                    value = "homeCarouselSectionComponentImages",
                    key = "#request.id()"
            )
    })
    public HomeCarouselSection update(
            UpdateCarouselSectionRequest request,
            @Nullable MultipartFile image
    ) {
        int id = request.id();
        HomeCarouselSection section = repository.findForUpdateById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeCarouselSection.NAME, id));
        String imageName = saveImageIfNotNull(
                section.getImageName(),
                image
        );
        section = applyChanges(
                imageName,
                request,
                section
        );

        return repository.save(section);
    }

    @Caching(evict = {
            @CacheEvict(
                    value = "homeCarouselSectionComponents",
                    key = "#id"
            ),
            @CacheEvict(
                    value = "homeCarouselSectionComponentImages",
                    key = "#id"
            )
    })
    public void delete(int id) {
        String imageName = getImageName(id);

        repository.deleteById(id);
        imageService.delete(imageName);
    }

    private HomeCarouselSection generateEntity(
            String imageName,
            CreateCarouselSectionRequest request
    ) {
        return new HomeCarouselSection(
                imageName,
                request.text(),
                request.displayOrder(),
                new Home(request.pageId())
        );
    }

    private String getImageName(int id) {
        return repository.findImageNameById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeCarouselSection.NAME, id));
    }

    private String saveImageIfNotNull(
            String currentImageName,
            MultipartFile image
    ) {
        return image == null
                ? currentImageName
                : imageService.save(image, currentImageName);
    }

    private HomeCarouselSection applyChanges(
            String imageName,
            UpdateCarouselSectionRequest request,
            HomeCarouselSection section
    ) {
        section.setImageName(imageName);
        section.setText(request.text());
        section.setDisplayOrder(request.displayOrder());

        return section;
    }
}
