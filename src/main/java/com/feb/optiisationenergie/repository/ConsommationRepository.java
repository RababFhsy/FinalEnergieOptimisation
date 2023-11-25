package com.feb.optiisationenergie.repository;

import com.feb.optiisationenergie.domain.Consommation;
import com.feb.optiisationenergie.domain.Preference;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Consommation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsommationRepository extends JpaRepository<Consommation, Long> {
    @Query("select consommation from Consommation consommation where consommation.user.login = ?#{principal.username}")
    List<Consommation> findByUserIsCurrentUser();
}
