package com.gungorefe.simpleportfolio.repository.page.component.home;

import com.gungorefe.simpleportfolio.entity.page.component.home.HomeDetailedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeDetailedCardRepository extends JpaRepository<HomeDetailedCard, Integer> {
    List<HomeDetailedCard> findAllByHome_Id(int homeId);

    @Query("""
            SELECT hdc.imageName
            FROM HomeDetailedCard hdc
            WHERE hdc.id = ?1
            """)
    Optional<String> findImageNameById(int id);

    @Query("""
            SELECT NEW HomeDetailedCard(hdc.id, hdc.imageName, hdc.home.id)
            FROM HomeDetailedCard hdc
            WHERE hdc.id = ?1
            """)
    Optional<HomeDetailedCard> findForUpdateById(int id);
}
