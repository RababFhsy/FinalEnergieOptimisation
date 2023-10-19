package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Energie;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Energie}.
 */
public interface EnergieService {
    /**
     * Save a energie.
     *
     * @param energie the entity to save.
     * @return the persisted entity.
     */
    Energie save(Energie energie);

    /**
     * Updates a energie.
     *
     * @param energie the entity to update.
     * @return the persisted entity.
     */
    Energie update(Energie energie);

    /**
     * Partially updates a energie.
     *
     * @param energie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Energie> partialUpdate(Energie energie);

    /**
     * Get all the energies.
     *
     * @return the list of entities.
     */
    List<Energie> findAll();

    /**
     * Get the "id" energie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Energie> findOne(Long id);

    /**
     * Delete the "id" energie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
