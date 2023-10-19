package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Anomalie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Anomalie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnomalieRepository extends JpaRepository<Anomalie, Long> {}
