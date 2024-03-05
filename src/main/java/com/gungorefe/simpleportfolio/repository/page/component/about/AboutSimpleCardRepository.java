package com.gungorefe.simpleportfolio.repository.page.component.about;

import com.gungorefe.simpleportfolio.entity.page.component.about.AboutSimpleCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AboutSimpleCardRepository extends JpaRepository<AboutSimpleCard, Integer> {
    List<AboutSimpleCard> findAllByAbout_Id(int aboutId);

    @Query("""
            SELECT sc.imageName
            FROM AboutSimpleCard sc
            WHERE sc.id = ?1
            """)
    Optional<String> findImageNameById(int id);

    @Query("""
            SELECT NEW AboutSimpleCard(sc.id, sc.imageName, sc.about.id)
            FROM AboutSimpleCard sc
            WHERE sc.id = ?1
            """)
    Optional<AboutSimpleCard> findForUpdateById(int id);
}
