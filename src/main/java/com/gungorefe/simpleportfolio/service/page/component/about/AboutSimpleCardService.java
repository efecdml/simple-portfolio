package com.gungorefe.simpleportfolio.service.page.component.about;

import com.gungorefe.simpleportfolio.dto.filestorage.Image;
import com.gungorefe.simpleportfolio.dto.page.component.CardDto;
import com.gungorefe.simpleportfolio.dto.page.component.CreateCardRequest;
import com.gungorefe.simpleportfolio.dto.page.component.UpdateCardRequest;
import com.gungorefe.simpleportfolio.entity.page.about.About;
import com.gungorefe.simpleportfolio.entity.page.component.about.AboutSimpleCard;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.component.about.AboutSimpleCardRepository;
import com.gungorefe.simpleportfolio.service.filestorage.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AboutSimpleCardService {
    private final AboutSimpleCardRepository repository;
    private final ImageService imageService;

    public AboutSimpleCard create(
            MultipartFile image,
            CreateCardRequest request
    ) {
        String imageName = imageService.save(image);
        AboutSimpleCard card = generateEntity(
                imageName,
                request
        );

        return repository.save(card);
    }

    public CardDto getDto(int id) {
        return repository.findById(id)
                .map(AboutSimpleCard::toDto)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(AboutSimpleCard.NAME, id));
    }

    public List<CardDto> getAllDtos(int aboutId) {
        List<AboutSimpleCard> cards = repository.findAllByAbout_Id(aboutId);

        return AboutSimpleCard.toDtoList(cards);
    }

    public Image getImage(int id) {
        String imageName = getImageName(id);

        return imageService.get(imageName);
    }

    public AboutSimpleCard update(
            UpdateCardRequest request,
            @Nullable MultipartFile image
    ) {
        int id = request.id();
        AboutSimpleCard card = repository.findForUpdateById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(AboutSimpleCard.NAME, id));
        String imageName = saveImageIfNotNull(
                card.getImageName(),
                image
        );
        card = applyChanges(
                card,
                imageName,
                request
        );

        return repository.save(card);
    }

    public void delete(int id) {
        String imageName = getImageName(id);

        repository.deleteById(id);
        imageService.delete(imageName);
    }

    private AboutSimpleCard generateEntity(
            String imageName,
            CreateCardRequest request
    ) {
        return new AboutSimpleCard(
                imageName,
                request.title(),
                request.text(),
                request.displayOrder(),
                new About(request.pageId())
        );
    }

    private String getImageName(int id) {
        return repository.findImageNameById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(AboutSimpleCard.NAME, id));
    }

    private String saveImageIfNotNull(
            String currentImageName,
            MultipartFile image
    ) {
        return image == null
                ? currentImageName
                : imageService.save(image, currentImageName);
    }

    private AboutSimpleCard applyChanges(
            AboutSimpleCard card,
            String imageName,
            UpdateCardRequest request
    ) {
        card.setImageName(imageName);
        card.setTitle(request.title());
        card.setText(request.text());
        card.setDisplayOrder(request.displayOrder());

        return card;
    }
}
