package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.Consommation;
import com.feb.optiisationenergie.domain.Preference;
import com.feb.optiisationenergie.repository.ConsommationRepository;
import com.feb.optiisationenergie.service.ConsommationService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.Consommation}.
 */
@RestController
@RequestMapping("/api")
public class ConsommationResource {

    private final Logger log = LoggerFactory.getLogger(ConsommationResource.class);

    private static final String ENTITY_NAME = "consommation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsommationService consommationService;

    private final ConsommationRepository consommationRepository;

    public ConsommationResource(ConsommationService consommationService, ConsommationRepository consommationRepository) {
        this.consommationService = consommationService;
        this.consommationRepository = consommationRepository;
    }

    /**
     * {@code POST  /consommations} : Create a new consommation.
     *
     * @param consommation the consommation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consommation, or with status {@code 400 (Bad Request)} if the consommation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consommations")
    public ResponseEntity<Consommation> createConsommation(@RequestBody Consommation consommation) throws URISyntaxException {
        log.debug("REST request to save Consommation : {}", consommation);
        if (consommation.getId() != null) {
            throw new BadRequestAlertException("A new consommation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consommation result = consommationService.save(consommation);
        return ResponseEntity
            .created(new URI("/api/consommations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consommations/:id} : Updates an existing consommation.
     *
     * @param id the id of the consommation to save.
     * @param consommation the consommation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consommation,
     * or with status {@code 400 (Bad Request)} if the consommation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consommation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consommations/{id}")
    public ResponseEntity<Consommation> updateConsommation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Consommation consommation
    ) throws URISyntaxException {
        log.debug("REST request to update Consommation : {}, {}", id, consommation);
        if (consommation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consommation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consommationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Consommation result = consommationService.update(consommation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consommation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consommations/:id} : Partial updates given fields of an existing consommation, field will ignore if it is null
     *
     * @param id the id of the consommation to save.
     * @param consommation the consommation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consommation,
     * or with status {@code 400 (Bad Request)} if the consommation is not valid,
     * or with status {@code 404 (Not Found)} if the consommation is not found,
     * or with status {@code 500 (Internal Server Error)} if the consommation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consommations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Consommation> partialUpdateConsommation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Consommation consommation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Consommation partially : {}, {}", id, consommation);
        if (consommation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consommation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consommationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Consommation> result = consommationService.partialUpdate(consommation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consommation.getId().toString())
        );
    }

    /**
     * {@code GET  /consommations} : get all the consommations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consommations in body.
     */
    @GetMapping("/consommations")
    public List<Consommation> getAllConsommations() {
        log.debug("REST request to get all Consommations");
        return consommationService.findAll();
    }

    /**
     * {@code GET  /consommations/:id} : get the "id" consommation.
     *
     * @param id the id of the consommation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consommation, or with status {@code 404 (Not Found)}.
     */
     @GetMapping("/consommations/user")
    public List<Consommation> getConsommationsByUser(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Consommations By User");
        return consommationRepository.findByUserIsCurrentUser();
    }

    @GetMapping("/consommations/{id}")
    public ResponseEntity<Consommation> getConsommation(@PathVariable Long id) {
        log.debug("REST request to get Consommation : {}", id);
        Optional<Consommation> consommation = consommationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consommation);
    }

    /**
     * {@code DELETE  /consommations/:id} : delete the "id" consommation.
     *
     * @param id the id of the consommation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consommations/{id}")
    public ResponseEntity<Void> deleteConsommation(@PathVariable Long id) {
        log.debug("REST request to delete Consommation : {}", id);
        consommationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
