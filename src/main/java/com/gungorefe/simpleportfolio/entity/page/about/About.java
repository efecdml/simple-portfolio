package com.gungorefe.simpleportfolio.entity.page.about;

import com.gungorefe.simpleportfolio.dto.page.about.AboutDto;
import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.Page;
import com.gungorefe.simpleportfolio.entity.page.component.SimpleCard;
import com.gungorefe.simpleportfolio.entity.page.component.about.AboutSimpleCard;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@NoArgsConstructor
@Setter
@Table(name = "about_pages")
@Entity
public class About extends Page {
    public static final String NAME = "about";
    private String imageName;
    private String title;
    private String text;
    @OneToMany(mappedBy = "about")
    private Collection<AboutSimpleCard> simpleCards;

    public About(int id) {
        super(id);
    }

    public About(int id, int localeId, String imageName) {
        super(id, new Locale(localeId));
        this.imageName = imageName;
    }

    public About(Locale locale, String imageName, String title, String text) {
        super(locale);
        this.imageName = imageName;
        this.title = title;
        this.text = text;
    }

    public AboutDto toDto() {
        return new AboutDto(
                id,
                imageName,
                title,
                text,
                SimpleCard.toDtoList(simpleCards)
        );
    }
}
