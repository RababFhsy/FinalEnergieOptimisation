package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.CapteurBoitier;
import com.feb.optiisationenergie.repository.CapteurBoitierRepository;
import com.feb.optiisationenergie.service.CapteurBoitierService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.CapteurBoitier}.
 */
@RestController
@RequestMapping("/api")
public class CapteurBoitierResource {

    private final Logger log = LoggerFactory.getLogger(CapteurBoitierResource.class);

    private static final String ENTITY_NAME = "capteurBoitier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapteurBoitierService capteurBoitierService;

    private final CapteurBoitierRepository capteurBoitierRepository;

    public CapteurBoitierResource(CapteurBoitierService capteurBoitierService, CapteurBoitierRepository capteurBoitierRepository) {
        this.capteurBoitierService = capteurBoitierService;
        this.capteurBoitierRepository = capteurBoitierRepository;
    }

    /**
     * {@code POST  /capteur-boitiers} : Create a new capteurBoitier.
     *
     * @param capteurBoitier the capteurBoitier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capteurBoitier, or with status {@code 400 (Bad Request)} if the capteurBoitier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capteur-boitiers")
    public ResponseEntity<CapteurBoitier> createCapteurBoitier(@RequestBody CapteurBoitier capteurBoitier) throws URISyntaxException {
        log.debug("REST request to save CapteurBoitier : {}", capteurBoitier);
        if (capteurBoitier.getId() != null) {
            throw new BadRequestAlertException("A new capteurBoitier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapteurBoitier result = capteurBoitierService.save(capteurBoitier);
        return ResponseEntity
            .created(new URI("/api/capteur-boitiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capteur-boitiers/:id} : Updates an existing capteurBoitier.
     *
     * @param id the id of the capteurBoitier to save.
     * @param capteurBoitier the capteurBoitier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capteurBoitier,
     * or with status {@code 400 (Bad Request)} if the capteurBoitier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capteurBoitier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capteur-boitiers/{id}")
    public ResponseEntity<CapteurBoitier> updateCapteurBoitier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CapteurBoitier capteurBoitier
    ) throws URISyntaxException {
        log.debug("REST request to update CapteurBoitier : {}, {}", id, capteurBoitier);
        if (capteurBoitier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capteurBoitier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capteurBoitierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CapteurBoitier result = capteurBoitierService.update(capteurBoitier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capteurBoitier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /capteur-boitiers/:id} : Partial updates given fields of an existing capteurBoitier, field will ignore if it is null
     *
     * @param id the id of the capteurBoitier to save.
     * @param capteurBoitier the capteurBoitier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capteurBoitier,
     * or with status {@code 400 (Bad Request)} if the capteurBoitier is not valid,
     * or with status {@code 404 (Not Found)} if the capteurBoitier is not found,
     * or with status {@code 500 (Internal Server Error)} if the capteurBoitier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/capteur-boitiers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CapteurBoitier> partialUpdateCapteurBoitier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CapteurBoitier capteurBoitier
    ) throws URISyntaxException {
        log.debug("REST request to partial update CapteurBoitier partially : {}, {}", id, capteurBoitier);
        if (capteurBoitier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capteurBoitier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capteurBoitierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CapteurBoitier> result = capteurBoitierService.partialUpdate(capteurBoitier);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capteurBoitier.getId().toString())
        );
    }

    /**
     * {@code GET  /capteur-boitiers} : get all the capteurBoitiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capteurBoitiers in body.
     */
    @GetMapping("/capteur-boitiers")
    public List<CapteurBoitier> getAllCapteurBoitiers() {
        log.debug("REST request to get all CapteurBoitiers");
        return capteurBoitierService.findAll();
    }

    /**
     * {@code GET  /capteur-boitiers/:id} : get the "id" capteurBoitier.
     *
     * @param id the id of the capteurBoitier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capteurBoitier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capteur-boitiers/{id}")
    public ResponseEntity<CapteurBoitier> getCapteurBoitier(@PathVariable Long id) {
        log.debug("REST request to get CapteurBoitier : {}", id);
        Optional<CapteurBoitier> capteurBoitier = capteurBoitierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capteurBoitier);
    }

    /**
     * {@code DELETE  /capteur-boitiers/:id} : delete the "id" capteurBoitier.
     *
     * @param id the id of the capteurBoitier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capteur-boitiers/{id}")
    public ResponseEntity<Void> deleteCapteurBoitier(@PathVariable Long id) {
        log.debug("REST request to delete CapteurBoitier : {}", id);
        capteurBoitierService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
