package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Batiment;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Batiment}.
 */
public interface BatimentService {
    /**
     * Save a batiment.
     *
     * @param batiment the entity to save.
     * @return the persisted entity.
     */
    Batiment save(Batiment batiment);

    /**
     * Updates a batiment.
     *
     * @param batiment the entity to update.
     * @return the persisted entity.
     */
    Batiment update(Batiment batiment);

    /**
     * Partially updates a batiment.
     *
     * @param batiment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Batiment> partialUpdate(Batiment batiment);

    /**
     * Get all the batiments.
     *
     * @return the list of entities.
     */
    List<Batiment> findAll();

    /**
     * Get the "id" batiment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Batiment> findOne(Long id);

    /**
     * Delete the "id" batiment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
