package com.gungorefe.simpleportfolio.repository.page.contact;

import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("""
            SELECT c
            FROM Contact c
            LEFT JOIN FETCH c.phones
            WHERE c.locale.name = ?1
            """)
    Optional<Contact> findWithPhonesByLocale_Name(LocaleName localeName);

    @Query("""
            SELECT NEW Contact(c.id, c.locale.id)
            FROM Contact c
            WHERE c.locale.name = ?1
            """)
    Optional<Contact> findForUpdateByLocale_Name(LocaleName localeName);

    boolean existsByLocale_Name(LocaleName localeName);
}
