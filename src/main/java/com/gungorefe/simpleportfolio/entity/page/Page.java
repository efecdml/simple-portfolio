package com.gungorefe.simpleportfolio.entity.page;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@MappedSuperclass
public class Page {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    protected int id;
    @JoinColumn(name = "locale_id")
    @OneToOne(fetch = FetchType.LAZY)
    protected Locale locale;

    public Page(int id) {
        this.id = id;
    }

    public Page(Locale locale) {
        this.locale = locale;
    }
}
