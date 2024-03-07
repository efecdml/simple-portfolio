package com.gungorefe.simpleportfolio.service.page.component.home;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.component.CardDto;
import com.gungorefe.simpleportfolio.dto.page.component.CreateCardRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdateCardRequest;
import com.gungorefe.simpleportfolio.entity.page.component.DetailedCard;
import com.gungorefe.simpleportfolio.entity.page.component.home.HomeDetailedCard;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.component.home.HomeDetailedCardRepository;
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
public class HomeDetailedCardService {
    private final ImageService imageService;
    private final HomeDetailedCardRepository repository;

    public HomeDetailedCard create(
            MultipartFile image,
            CreateCardRequest request
    ) {
        String imageName = imageService.save(image);
        HomeDetailedCard card = generateEntity(
                imageName,
                request
        );

        return repository.save(card);
    }

    @Cacheable(
            value = "homeDetailedCardComponents",
            key = "#id"
    )
    public CardDto getDto(int id) {
        return repository.findById(id)
                .map(HomeDetailedCard::toDto)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeDetailedCard.NAME, id));
    }

    public List<CardDto> getAllDtos(int homeId) {
        List<HomeDetailedCard> cards = repository.findAllByHome_Id(homeId);

        return DetailedCard.toDtoList(cards);
    }

    @Cacheable(
            value = "homeDetailedCardComponentImages",
            key = "#id"
    )
    public Image getImage(int id) {
        String imageName = getImageName(id);

        return imageService.get(imageName);
    }

    @Caching(evict = {
            @CacheEvict(
                    value = "homeDetailedCardComponents",
                    key = "#request.id()"
            ),
            @CacheEvict(
                    value = "homeDetailedCardComponentImages",
                    key = "#request.id()"
            )
    })
    public HomeDetailedCard update(
            UpdateCardRequest request,
            @Nullable MultipartFile image
    ) {
        int id = request.id();
        HomeDetailedCard card = repository.findForUpdateById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeDetailedCard.NAME, id));
        String imageName = saveImageIfNotNull(
                card.getImageName(),
                image
        );
        card = applyChanges(
                imageName,
                request,
                card
        );

        return repository.save(card);
    }

    @Caching(evict = {
            @CacheEvict(
                    value = "homeDetailedCardComponents",
                    key = "#id"
            ),
            @CacheEvict(
                    value = "homeDetailedCardComponentImages",
                    key = "#id"
            )
    })
    public void delete(int id) {
        String imageName = getImageName(id);

        repository.deleteById(id);
        imageService.delete(imageName);
    }

    private HomeDetailedCard generateEntity(
            String imageName,
            CreateCardRequest request
    ) {
        return new HomeDetailedCard(
                imageName,
                request.title(),
                request.text(),
                request.displayOrder(),
                new Home(request.pageId())
        );
    }

    private String getImageName(int id) {
        return repository.findImageNameById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(HomeDetailedCard.NAME, id));
    }

    private String saveImageIfNotNull(
            String currentImageName,
            MultipartFile image
    ) {
        return image == null
                ? currentImageName
                : imageService.save(image, currentImageName);
    }

    private HomeDetailedCard applyChanges(
            String imageName,
            UpdateCardRequest request,
            HomeDetailedCard card
    ) {
        card.setImageName(imageName);
        card.setTitle(request.title());
        card.setText(request.text());
        card.setDisplayOrder(request.displayOrder());

        return card;
    }
}
