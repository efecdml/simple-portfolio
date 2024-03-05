package com.gungorefe.simpleportfolio.repository.page.home;

import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<Home, Integer> {
    @Query("""
            SELECT h
            FROM Home h
            LEFT JOIN FETCH h.carouselSections
            WHERE h.locale.name = ?1
            """)
    Optional<Home> findWithCarouselSectionsByLocale_Name(LocaleName localeName);

    @Query("""
            SELECT NEW Home(h.id, h.locale.id)
            FROM Home h
            WHERE h.locale.name = ?1
            """)
    Optional<Home> findForUpdateByLocale_Name(LocaleName localeName);

    boolean existsByLocale_Name(LocaleName localeName);
}
