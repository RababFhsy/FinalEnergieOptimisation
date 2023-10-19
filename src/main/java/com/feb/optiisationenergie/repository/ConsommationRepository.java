package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Consommation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Consommation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsommationRepository extends JpaRepository<Consommation, Long> {}
