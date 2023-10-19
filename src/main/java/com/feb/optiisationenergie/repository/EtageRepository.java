package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Etage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Etage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtageRepository extends JpaRepository<Etage, Long> {}
