package com.gungorefe.simpleportfolio.entity.page;

import com.gungorefe.simpleportfolio.entity.page.home.Home;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "locales")
@Entity
public class Locale {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private int id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private LocaleName name;
    @OneToOne(mappedBy = "locale")
    private Home home;

    public Locale(int id) {
        this.id = id;
    }

    public Locale(LocaleName name) {
        this.name = name;
    }
}
