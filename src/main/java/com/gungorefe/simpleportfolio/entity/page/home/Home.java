package com.gungorefe.simpleportfolio.entity.page.home;

import com.gungorefe.simpleportfolio.dto.page.home.HomeDto;
import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.Page;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
                secondText
        );
    }
}
