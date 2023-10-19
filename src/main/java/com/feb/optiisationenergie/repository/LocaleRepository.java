package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Locale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocaleRepository extends JpaRepository<Locale, Long> {}
