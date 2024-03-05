package com.gungorefe.simpleportfolio.repository.page.component.home;

import com.gungorefe.simpleportfolio.entity.page.component.home.HomeCarouselSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeCarouselSectionRepository extends JpaRepository<HomeCarouselSection, Integer> {
    List<HomeCarouselSection> findAllByHome_Id(int homeId);

    @Query("""
            SELECT hcs.imageName
            FROM HomeCarouselSection hcs
            WHERE hcs.id = ?1
            """)
    Optional<String> findImageNameById(int id);

    @Query("""
            SELECT NEW HomeCarouselSection(hcs.id, hcs.imageName, hcs.home.id)
            FROM HomeCarouselSection hcs
            WHERE hcs.id = ?1
            """)
    Optional<HomeCarouselSection> findForUpdateById(int id);
}
