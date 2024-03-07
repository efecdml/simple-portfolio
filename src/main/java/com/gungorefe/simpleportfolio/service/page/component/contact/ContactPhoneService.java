package com.gungorefe.simpleportfolio.service.page.component.contact;

import com.gungorefe.simpleportfolio.dto.page.component.CreatePhoneRequest;
import com.gungorefe.simpleportfolio.dto.page.component.PhoneDto;
import com.gungorefe.simpleportfolio.dto.page.component.UpdatePhoneRequest;
import com.gungorefe.simpleportfolio.entity.page.component.Phone;
import com.gungorefe.simpleportfolio.entity.page.component.contact.ContactPhone;
import com.gungorefe.simpleportfolio.entity.page.contact.Contact;
import com.gungorefe.simpleportfolio.exception.ExceptionFactory;
import com.gungorefe.simpleportfolio.repository.page.component.contact.ContactPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactPhoneService {
    private final ContactPhoneRepository repository;

    public ContactPhone create(CreatePhoneRequest request) {
        ContactPhone phone = generateEntity(request);

        return repository.save(phone);
    }

    @Cacheable(
            value = "contactPhoneComponents",
            key = "#id"
    )
    public PhoneDto getDto(int id) {
        return repository.findById(id)
                .map(Phone::toDto)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(ContactPhone.NAME, id));
    }

    public List<PhoneDto> getAllDtos(int contactId) {
        List<ContactPhone> phones = repository.findAllByContact_Id(contactId);

        return Phone.toDtoList(phones);
    }

    @CacheEvict(
            value = "contactPhoneComponents",
            key = "#request.id()"
    )
    public ContactPhone update(UpdatePhoneRequest request) {
        int id = request.id();
        ContactPhone phone = repository.findForUpdateById(id)
                .orElseThrow(() -> ExceptionFactory.componentNotFoundException(ContactPhone.NAME, id));
        phone = applyChanges(
                phone,
                request
        );

        return repository.save(phone);
    }

    @CacheEvict(
            value = "contactPhoneComponents",
            key = "#id"
    )
    public void delete(int id) {
        repository.deleteById(id);
    }

    private ContactPhone generateEntity(CreatePhoneRequest request) {
        return new ContactPhone(
                request.tag(),
                request.number(),
                request.displayOrder(),
                new Contact(request.pageId())
        );
    }

    private ContactPhone applyChanges(
            ContactPhone phone,
            UpdatePhoneRequest request
    ) {
        phone.setTag(request.tag());
        phone.setNumber(request.number());
        phone.setDisplayOrder(request.displayOrder());

        return phone;
    }
}
