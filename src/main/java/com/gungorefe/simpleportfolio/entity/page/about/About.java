package com.gungorefe.simpleportfolio.entity.page.about;

import com.gungorefe.simpleportfolio.dto.page.about.AboutDto;
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
@Table(name = "about_pages")
@Entity
public class About extends Page {
    public static final String NAME = "about";
    private String imageName;
    private String title;
    private String text;

    public About(int id, int localeId, String imageName) {
        super(id, new Locale(localeId));
        this.imageName = imageName;
    }

    public About(int id, String imageName, String title, String text) {
        super(id);
        this.imageName = imageName;
        this.title = title;
        this.text = text;
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
                text
        );
    }
}
