package com.gungorefe.simpleportfolio.entity.page.home;

import com.gungorefe.simpleportfolio.dto.page.home.HomeDto;
import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.Page;
import com.gungorefe.simpleportfolio.entity.page.component.CarouselSection;
import com.gungorefe.simpleportfolio.entity.page.component.home.HomeCarouselSection;
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
@Table(name = "home_pages")
@Entity
public class Home extends Page {
    public static final String NAME = "home";
    private String title;
    private String text;
    private String secondTitle;
    private String secondText;
    @OneToMany(mappedBy = "home")
    private Collection<HomeCarouselSection> carouselSections;

    public Home(int id) {
        super(id);
    }

    public Home(int id, int localeId) {
        super(id, new Locale(localeId));
    }

    public Home(Locale locale, String title, String text, String secondTitle, String secondText) {
        super(locale);
        this.title = title;
        this.text = text;
        this.secondTitle = secondTitle;
        this.secondText = secondText;
    }

    public HomeDto toDto() {
        return new HomeDto(
                id,
                title,
                text,
                secondTitle,
                secondText,
                CarouselSection.toDtoList(carouselSections)
        );
    }
}
