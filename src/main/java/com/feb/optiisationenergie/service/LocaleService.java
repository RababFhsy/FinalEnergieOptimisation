package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Locale;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Locale}.
 */
public interface LocaleService {
    /**
     * Save a locale.
     *
     * @param locale the entity to save.
     * @return the persisted entity.
     */
    Locale save(Locale locale);

    /**
     * Updates a locale.
     *
     * @param locale the entity to update.
     * @return the persisted entity.
     */
    Locale update(Locale locale);

    /**
     * Partially updates a locale.
     *
     * @param locale the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Locale> partialUpdate(Locale locale);

    /**
     * Get all the locales.
     *
     * @return the list of entities.
     */
    List<Locale> findAll();

    /**
     * Get the "id" locale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Locale> findOne(Long id);

    /**
     * Delete the "id" locale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
