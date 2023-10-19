package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Capteur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Capteur}.
 */
public interface CapteurService {
    /**
     * Save a capteur.
     *
     * @param capteur the entity to save.
     * @return the persisted entity.
     */
    Capteur save(Capteur capteur);

    /**
     * Updates a capteur.
     *
     * @param capteur the entity to update.
     * @return the persisted entity.
     */
    Capteur update(Capteur capteur);

    /**
     * Partially updates a capteur.
     *
     * @param capteur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Capteur> partialUpdate(Capteur capteur);

    /**
     * Get all the capteurs.
     *
     * @return the list of entities.
     */
    List<Capteur> findAll();

    /**
     * Get the "id" capteur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Capteur> findOne(Long id);

    /**
     * Delete the "id" capteur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
