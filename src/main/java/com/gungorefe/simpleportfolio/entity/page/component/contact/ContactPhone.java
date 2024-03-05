package com.gungorefe.simpleportfolio.entity.page.component.contact;

import com.gungorefe.simpleportfolio.entity.page.component.Phone;
import com.gungorefe.simpleportfolio.entity.page.contact.Contact;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "contact_phone_components")
@Entity
public class ContactPhone extends Phone {
    public static final String NAME = "contact phone";
    @JoinColumn(name = "contact_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Contact contact;

    public ContactPhone(int id, int contactId) {
        super(id);
        this.contact = new Contact(contactId);
    }

    public ContactPhone(String tag, String number, int displayOrder, Contact contact) {
        super(tag, number, displayOrder);
        this.contact = contact;
    }
}
