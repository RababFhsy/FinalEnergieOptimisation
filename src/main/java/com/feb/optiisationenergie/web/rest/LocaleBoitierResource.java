package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.LocaleBoitier;
import com.feb.optiisationenergie.repository.LocaleBoitierRepository;
import com.feb.optiisationenergie.service.LocaleBoitierService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.LocaleBoitier}.
 */
@RestController
@RequestMapping("/api")
public class LocaleBoitierResource {

    private final Logger log = LoggerFactory.getLogger(LocaleBoitierResource.class);

    private static final String ENTITY_NAME = "localeBoitier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocaleBoitierService localeBoitierService;

    private final LocaleBoitierRepository localeBoitierRepository;

    public LocaleBoitierResource(LocaleBoitierService localeBoitierService, LocaleBoitierRepository localeBoitierRepository) {
        this.localeBoitierService = localeBoitierService;
        this.localeBoitierRepository = localeBoitierRepository;
    }

    /**
     * {@code POST  /locale-boitiers} : Create a new localeBoitier.
     *
     * @param localeBoitier the localeBoitier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localeBoitier, or with status {@code 400 (Bad Request)} if the localeBoitier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locale-boitiers")
    public ResponseEntity<LocaleBoitier> createLocaleBoitier(@RequestBody LocaleBoitier localeBoitier) throws URISyntaxException {
        log.debug("REST request to save LocaleBoitier : {}", localeBoitier);
        if (localeBoitier.getId() != null) {
            throw new BadRequestAlertException("A new localeBoitier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocaleBoitier result = localeBoitierService.save(localeBoitier);
        return ResponseEntity
            .created(new URI("/api/locale-boitiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locale-boitiers/:id} : Updates an existing localeBoitier.
     *
     * @param id the id of the localeBoitier to save.
     * @param localeBoitier the localeBoitier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localeBoitier,
     * or with status {@code 400 (Bad Request)} if the localeBoitier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localeBoitier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locale-boitiers/{id}")
    public ResponseEntity<LocaleBoitier> updateLocaleBoitier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocaleBoitier localeBoitier
    ) throws URISyntaxException {
        log.debug("REST request to update LocaleBoitier : {}, {}", id, localeBoitier);
        if (localeBoitier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localeBoitier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localeBoitierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LocaleBoitier result = localeBoitierService.update(localeBoitier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, localeBoitier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /locale-boitiers/:id} : Partial updates given fields of an existing localeBoitier, field will ignore if it is null
     *
     * @param id the id of the localeBoitier to save.
     * @param localeBoitier the localeBoitier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localeBoitier,
     * or with status {@code 400 (Bad Request)} if the localeBoitier is not valid,
     * or with status {@code 404 (Not Found)} if the localeBoitier is not found,
     * or with status {@code 500 (Internal Server Error)} if the localeBoitier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/locale-boitiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LocaleBoitier> partialUpdateLocaleBoitier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LocaleBoitier localeBoitier
    ) throws URISyntaxException {
        log.debug("REST request to partial update LocaleBoitier partially : {}, {}", id, localeBoitier);
        if (localeBoitier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, localeBoitier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localeBoitierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocaleBoitier> result = localeBoitierService.partialUpdate(localeBoitier);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, localeBoitier.getId().toString())
        );
    }

    /**
     * {@code GET  /locale-boitiers} : get all the localeBoitiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localeBoitiers in body.
     */
    @GetMapping("/locale-boitiers")
    public List<LocaleBoitier> getAllLocaleBoitiers() {
        log.debug("REST request to get all LocaleBoitiers");
        return localeBoitierService.findAll();
    }

    /**
     * {@code GET  /locale-boitiers/:id} : get the "id" localeBoitier.
     *
     * @param id the id of the localeBoitier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localeBoitier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locale-boitiers/{id}")
    public ResponseEntity<LocaleBoitier> getLocaleBoitier(@PathVariable Long id) {
        log.debug("REST request to get LocaleBoitier : {}", id);
        Optional<LocaleBoitier> localeBoitier = localeBoitierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localeBoitier);
    }

    /**
     * {@code DELETE  /locale-boitiers/:id} : delete the "id" localeBoitier.
     *
     * @param id the id of the localeBoitier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locale-boitiers/{id}")
    public ResponseEntity<Void> deleteLocaleBoitier(@PathVariable Long id) {
        log.debug("REST request to delete LocaleBoitier : {}", id);
        localeBoitierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
