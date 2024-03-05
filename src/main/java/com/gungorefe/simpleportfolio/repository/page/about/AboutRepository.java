package com.gungorefe.simpleportfolio.repository.page.about;

import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.about.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutRepository extends JpaRepository<About, Integer> {
    @Query("""
            SELECT a
            FROM About a
            LEFT JOIN FETCH a.simpleCards
            WHERE a.locale.name = ?1
            """)
    Optional<About> findWithSimpleCardsByLocale_Name(LocaleName localeName);

    @Query("""
            SELECT NEW About(a.id, a.locale.id, a.imageName)
            FROM About a
            WHERE a.locale.name = ?1
            """)
    Optional<About> findForUpdateByLocale_Name(LocaleName localeName);

    @Query("""
            SELECT a.imageName
            FROM About a
            WHERE a.locale.name = ?1
            """)
    Optional<String> findImageNameByLocale_Name(LocaleName localeName);

    boolean existsByLocale_Name(LocaleName localeName);
}
