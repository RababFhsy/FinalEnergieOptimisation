package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Prediction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prediction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {}
