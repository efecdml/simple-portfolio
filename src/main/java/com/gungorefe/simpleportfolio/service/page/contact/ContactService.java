package com.gungorefe.simpleportfolio.service.page.contact;

import com.gungorefe.simpleportfolio.dto.page.contact.ContactDto;
import com.gungorefe.simpleportfolio.dto.page.contact.UpdateContactRequest;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.contact.Contact;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.contact.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContactService {
    private final ContactRepository repository;

    @Cacheable(
            value = "contactPages",
            key = "#localeName.value"
    )
    public ContactDto getDto(LocaleName localeName) {
        return repository.findWithPhonesByLocale_Name(localeName)
                .map(Contact::toDto)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(Contact.NAME, localeName));
    }

    @CacheEvict(
            value = "contactPages",
            key = "#localeName.value"
    )
    public Contact update(
            LocaleName localeName,
            UpdateContactRequest request
    ) {
        Contact contact = repository.findForUpdateByLocale_Name(localeName)
                .orElseThrow(() -> ExceptionFactory.pageNotFoundException(Contact.NAME, localeName));
        contact = applyChanges(
                contact,
                request
        );

        return repository.save(contact);
    }

    private Contact applyChanges(
            Contact contact,
            UpdateContactRequest request
    ) {
        contact.setTitle(request.title());
        contact.setText(request.text());
        contact.setEmail(request.email());
        contact.setLocation(request.location());
        contact.setWorkingDays(request.workingDays());
        contact.setWorkingHours(request.workingHours());
        contact.setGoogleMapsCoordination(request.googleMapsCoordination());

        return contact;
    }
}
