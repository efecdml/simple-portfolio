package com.gungorefe.simpleportfolio.repository.page.component.contact;

import com.gungorefe.simpleportfolio.entity.page.component.contact.ContactPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactPhoneRepository extends JpaRepository<ContactPhone, Integer> {
    List<ContactPhone> findAllByContact_Id(int contactId);

    @Query("""
            SELECT NEW ContactPhone(cp.id, cp.contact.id)
            FROM ContactPhone cp
            WHERE cp.id = ?1
            """)
    Optional<ContactPhone> findForUpdateById(int id);
}
