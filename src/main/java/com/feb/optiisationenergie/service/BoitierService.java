package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Boitier;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Boitier}.
 */
public interface BoitierService {
    /**
     * Save a boitier.
     *
     * @param boitier the entity to save.
     * @return the persisted entity.
     */
    Boitier save(Boitier boitier);

    /**
     * Updates a boitier.
     *
     * @param boitier the entity to update.
     * @return the persisted entity.
     */
    Boitier update(Boitier boitier);

    /**
     * Partially updates a boitier.
     *
     * @param boitier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Boitier> partialUpdate(Boitier boitier);

    /**
     * Get all the boitiers.
     *
     * @return the list of entities.
     */
    List<Boitier> findAll();

    /**
     * Get the "id" boitier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Boitier> findOne(Long id);

    /**
     * Delete the "id" boitier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
