package com.gungorefe.simpleportfolio.entity.page.component.about;

import com.gungorefe.simpleportfolio.entity.page.about.About;
import com.gungorefe.simpleportfolio.entity.page.component.SimpleCard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "about_simple_card_components")
@Entity
public class AboutSimpleCard extends SimpleCard {
    public static final String NAME = "about simple card";
    @JoinColumn(name = "about_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private About about;

    public AboutSimpleCard(int id, String imageName, int aboutId) {
        super(id, imageName);
        this.about = new About(aboutId);
    }

    public AboutSimpleCard(String imageName, String title, String text, int displayOrder, About about) {
        super(imageName, title, text, displayOrder);
        this.about = about;
    }
}
