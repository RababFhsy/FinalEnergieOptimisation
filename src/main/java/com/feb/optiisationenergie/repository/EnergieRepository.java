package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Energie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Energie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnergieRepository extends JpaRepository<Energie, Long> {}
