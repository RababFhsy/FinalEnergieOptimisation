package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Consommation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Consommation}.
 */
public interface ConsommationService {
    /**
     * Save a consommation.
     *
     * @param consommation the entity to save.
     * @return the persisted entity.
     */
    Consommation save(Consommation consommation);

    /**
     * Updates a consommation.
     *
     * @param consommation the entity to update.
     * @return the persisted entity.
     */
    Consommation update(Consommation consommation);

    /**
     * Partially updates a consommation.
     *
     * @param consommation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Consommation> partialUpdate(Consommation consommation);

    /**
     * Get all the consommations.
     *
     * @return the list of entities.
     */
    List<Consommation> findAll();

    /**
     * Get the "id" consommation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */


    Optional<Consommation> findOne(Long id);

    /**
     * Delete the "id" consommation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
