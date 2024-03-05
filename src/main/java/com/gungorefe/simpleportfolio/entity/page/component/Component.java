package com.gungorefe.simpleportfolio.entity.page.component;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@MappedSuperclass
public class Component {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    protected int id;
}
