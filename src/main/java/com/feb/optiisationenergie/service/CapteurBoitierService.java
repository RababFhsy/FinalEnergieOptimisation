package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.CapteurBoitier;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CapteurBoitier}.
 */
public interface CapteurBoitierService {
    /**
     * Save a capteurBoitier.
     *
     * @param capteurBoitier the entity to save.
     * @return the persisted entity.
     */
    CapteurBoitier save(CapteurBoitier capteurBoitier);

    /**
     * Updates a capteurBoitier.
     *
     * @param capteurBoitier the entity to update.
     * @return the persisted entity.
     */
    CapteurBoitier update(CapteurBoitier capteurBoitier);

    /**
     * Partially updates a capteurBoitier.
     *
     * @param capteurBoitier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CapteurBoitier> partialUpdate(CapteurBoitier capteurBoitier);

    /**
     * Get all the capteurBoitiers.
     *
     * @return the list of entities.
     */
    List<CapteurBoitier> findAll();

    /**
     * Get the "id" capteurBoitier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CapteurBoitier> findOne(Long id);

    /**
     * Delete the "id" capteurBoitier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
