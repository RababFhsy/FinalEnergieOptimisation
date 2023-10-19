package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Anomalie;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Anomalie}.
 */
public interface AnomalieService {
    /**
     * Save a anomalie.
     *
     * @param anomalie the entity to save.
     * @return the persisted entity.
     */
    Anomalie save(Anomalie anomalie);

    /**
     * Updates a anomalie.
     *
     * @param anomalie the entity to update.
     * @return the persisted entity.
     */
    Anomalie update(Anomalie anomalie);

    /**
     * Partially updates a anomalie.
     *
     * @param anomalie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Anomalie> partialUpdate(Anomalie anomalie);

    /**
     * Get all the anomalies.
     *
     * @return the list of entities.
     */
    List<Anomalie> findAll();

    /**
     * Get the "id" anomalie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Anomalie> findOne(Long id);

    /**
     * Delete the "id" anomalie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
