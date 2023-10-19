package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.CapteurBoitier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CapteurBoitier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapteurBoitierRepository extends JpaRepository<CapteurBoitier, Long> {}
