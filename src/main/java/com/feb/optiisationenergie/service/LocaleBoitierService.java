package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.LocaleBoitier;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LocaleBoitier}.
 */
public interface LocaleBoitierService {
    /**
     * Save a localeBoitier.
     *
     * @param localeBoitier the entity to save.
     * @return the persisted entity.
     */
    LocaleBoitier save(LocaleBoitier localeBoitier);

    /**
     * Updates a localeBoitier.
     *
     * @param localeBoitier the entity to update.
     * @return the persisted entity.
     */
    LocaleBoitier update(LocaleBoitier localeBoitier);

    /**
     * Partially updates a localeBoitier.
     *
     * @param localeBoitier the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocaleBoitier> partialUpdate(LocaleBoitier localeBoitier);

    /**
     * Get all the localeBoitiers.
     *
     * @return the list of entities.
     */
    List<LocaleBoitier> findAll();

    /**
     * Get the "id" localeBoitier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocaleBoitier> findOne(Long id);

    /**
     * Delete the "id" localeBoitier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
