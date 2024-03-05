package com.gungorefe.simpleportfolio.entity.page.contact;

import com.gungorefe.simpleportfolio.dto.page.contact.ContactDto;
import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.Page;
import com.gungorefe.simpleportfolio.entity.page.component.Phone;
import com.gungorefe.simpleportfolio.entity.page.component.contact.ContactPhone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "contact_pages")
@Entity
public class Contact extends Page {
    public static final String NAME = "contact";
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    private String email;
    @Column(columnDefinition = "text")
    private String location;
    private String workingDays;
    private String workingHours;
    @Column(columnDefinition = "text")
    private String googleMapsCoordination;
    @OneToMany(mappedBy = "contact")
    private Collection<ContactPhone> phones;

    public Contact(int id) {
        super(id);
    }

    public Contact(int id, int localeId) {
        super(id, new Locale(localeId));
    }

    public Contact(Locale locale, String title, String text, String email, String location, String workingDays, String workingHours, String googleMapsCoordination) {
        super(locale);
        this.title = title;
        this.text = text;
        this.email = email;
        this.location = location;
        this.workingDays = workingDays;
        this.workingHours = workingHours;
        this.googleMapsCoordination = googleMapsCoordination;
    }

    public ContactDto toDto() {
        return new ContactDto(
                title,
                text,
                email,
                location,
                workingDays,
                workingHours,
                googleMapsCoordination,
                Phone.toDtoList(phones)
        );
    }
}
