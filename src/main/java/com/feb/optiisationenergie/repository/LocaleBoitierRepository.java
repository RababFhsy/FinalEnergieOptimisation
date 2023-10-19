package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.LocaleBoitier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocaleBoitier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocaleBoitierRepository extends JpaRepository<LocaleBoitier, Long> {}
