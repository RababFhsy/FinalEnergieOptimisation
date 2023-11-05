package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.Preference;
import com.feb.optiisationenergie.repository.PreferenceRepository;
import com.feb.optiisationenergie.service.PreferenceService;
import com.feb.optiisationenergie.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.feb.optiisationenergie.domain.Preference}.
 */
@RestController
@RequestMapping("/api")
public class PreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PreferenceResource.class);

    private static final String ENTITY_NAME = "preference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferenceService preferenceService;

    private final PreferenceRepository preferenceRepository;

    public PreferenceResource(PreferenceService preferenceService, PreferenceRepository preferenceRepository) {
        this.preferenceService = preferenceService;
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * {@code POST  /preferences} : Create a new preference.
     *
     * @param preference the preference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preference, or with status {@code 400 (Bad Request)} if the preference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferences")
    public ResponseEntity<Preference> createPreference(@RequestBody Preference preference) throws URISyntaxException {
        log.debug("REST request to save Preference : {}", preference);
        if (preference.getId() != null) {
            throw new BadRequestAlertException("A new preference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Preference result = preferenceService.save(preference);
        return ResponseEntity
            .created(new URI("/api/preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preferences/:id} : Updates an existing preference.
     *
     * @param id the id of the preference to save.
     * @param preference the preference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preference,
     * or with status {@code 400 (Bad Request)} if the preference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferences/{id}")
    public ResponseEntity<Preference> updatePreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Preference preference
    ) throws URISyntaxException {
        log.debug("REST request to update Preference : {}, {}", id, preference);
        if (preference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Preference result = preferenceService.update(preference);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preference.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferences/:id} : Partial updates given fields of an existing preference, field will ignore if it is null
     *
     * @param id the id of the preference to save.
     * @param preference the preference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preference,
     * or with status {@code 400 (Bad Request)} if the preference is not valid,
     * or with status {@code 404 (Not Found)} if the preference is not found,
     * or with status {@code 500 (Internal Server Error)} if the preference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Preference> partialUpdatePreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Preference preference
    ) throws URISyntaxException {
        log.debug("REST request to partial update Preference partially : {}, {}", id, preference);
        if (preference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Preference> result = preferenceService.partialUpdate(preference);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preference.getId().toString())
        );
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public List<Preference> getAllPreferences(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Preferences");
        return preferenceService.findAll();
    }

    @GetMapping("/preferences/user")
    public List<Preference> getPreferencesByUser(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Preferences By User");
        return preferenceRepository.findByUserIsCurrentUser();
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preference.
     *
     * @param id the id of the preference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<Preference> getPreference(@PathVariable Long id) {
        log.debug("REST request to get Preference : {}", id);
        Optional<Preference> preference = preferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preference);
    }

    /**
     * {@code DELETE  /preferences/:id} : delete the "id" preference.
     *
     * @param id the id of the preference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferences/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        log.debug("REST request to delete Preference : {}", id);
        preferenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
