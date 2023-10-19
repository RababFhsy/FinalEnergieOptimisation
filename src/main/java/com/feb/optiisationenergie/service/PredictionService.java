package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Prediction;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Prediction}.
 */
public interface PredictionService {
    /**
     * Save a prediction.
     *
     * @param prediction the entity to save.
     * @return the persisted entity.
     */
    Prediction save(Prediction prediction);

    /**
     * Updates a prediction.
     *
     * @param prediction the entity to update.
     * @return the persisted entity.
     */
    Prediction update(Prediction prediction);

    /**
     * Partially updates a prediction.
     *
     * @param prediction the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Prediction> partialUpdate(Prediction prediction);

    /**
     * Get all the predictions.
     *
     * @return the list of entities.
     */
    List<Prediction> findAll();

    /**
     * Get the "id" prediction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prediction> findOne(Long id);

    /**
     * Delete the "id" prediction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
