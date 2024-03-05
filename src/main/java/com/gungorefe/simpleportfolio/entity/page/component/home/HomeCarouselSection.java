package com.gungorefe.simpleportfolio.entity.page.component.home;

import com.gungorefe.simpleportfolio.entity.page.component.CarouselSection;
import com.gungorefe.simpleportfolio.entity.page.home.Home;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "home_carousel_section_components")
@Entity
public class HomeCarouselSection extends CarouselSection {
    public static final String NAME = "home carousel section";
    @JoinColumn(name = "home_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Home home;

    public HomeCarouselSection(int id, String imageName, int homeId) {
        super(id, imageName);
        this.home = new Home(homeId);
    }

    public HomeCarouselSection(String imageName, String text, int displayOrder, Home home) {
        super(imageName, text, displayOrder);
        this.home = home;
    }
}
