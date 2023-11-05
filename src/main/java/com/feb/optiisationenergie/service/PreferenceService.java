package com.feb.optiisationenergie.service;

import com.feb.optiisationenergie.domain.Preference;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Preference}.
 */
public interface PreferenceService {
    /**
     * Save a preference.
     *
     * @param preference the entity to save.
     * @return the persisted entity.
     */
    Preference save(Preference preference);

    /**
     * Updates a preference.
     *
     * @param preference the entity to update.
     * @return the persisted entity.
     */
    Preference update(Preference preference);

    /**
     * Partially updates a preference.
     *
     * @param preference the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Preference> partialUpdate(Preference preference);

    /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    List<Preference> findAll();

     /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    // List<Preference> findByUserIsCurrentUser();
;

    /**
     * Get all the preferences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Preference> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" preference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Preference> findOne(Long id);

    /**
     * Delete the "id" preference.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
