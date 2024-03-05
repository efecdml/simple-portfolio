package com.gungorefe.simpleportfolio.entity.page.component.home;

import com.gungorefe.simpleportfolio.entity.page.component.DetailedCard;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "home_detailed_card_components")
@Entity
public class HomeDetailedCard extends DetailedCard {
    public static final String NAME = "home detailed section";
    @JoinColumn(name = "home_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Home home;

    public HomeDetailedCard(int id, String imageName, int homeId) {
        super(id, imageName);
        this.home = new Home(homeId);
    }

    public HomeDetailedCard(String imageName, String title, String text, int displayOrder, Home home) {
        super(imageName, title, text, displayOrder);
        this.home = home;
    }
}
