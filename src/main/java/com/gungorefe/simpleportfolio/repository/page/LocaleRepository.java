package com.gungorefe.simpleportfolio.repository.page;

import com.gungorefe.simpleportfolio.entity.page.Locale;
import com.gungorefe.simpleportfolio.entity.page.LocaleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocaleRepository extends JpaRepository<Locale, Integer> {
    boolean existsByName(LocaleName name);
}
