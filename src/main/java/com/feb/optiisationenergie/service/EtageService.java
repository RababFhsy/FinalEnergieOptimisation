package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Etage;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Etage}.
 */
public interface EtageService {
    /**
     * Save a etage.
     *
     * @param etage the entity to save.
     * @return the persisted entity.
     */
    Etage save(Etage etage);

    /**
     * Updates a etage.
     *
     * @param etage the entity to update.
     * @return the persisted entity.
     */
    Etage update(Etage etage);

    /**
     * Partially updates a etage.
     *
     * @param etage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Etage> partialUpdate(Etage etage);

    /**
     * Get all the etages.
     *
     * @return the list of entities.
     */
    List<Etage> findAll();

    /**
     * Get the "id" etage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etage> findOne(Long id);

    /**
     * Delete the "id" etage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
