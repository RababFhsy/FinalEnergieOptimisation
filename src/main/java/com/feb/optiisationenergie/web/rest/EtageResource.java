package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.Etage;
import com.feb.optiisationenergie.repository.EtageRepository;
import com.feb.optiisationenergie.service.EtageService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.Etage}.
 */
@RestController
@RequestMapping("/api")
public class EtageResource {

    private final Logger log = LoggerFactory.getLogger(EtageResource.class);

    private static final String ENTITY_NAME = "etage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtageService etageService;

    private final EtageRepository etageRepository;

    public EtageResource(EtageService etageService, EtageRepository etageRepository) {
        this.etageService = etageService;
        this.etageRepository = etageRepository;
    }

    /**
     * {@code POST  /etages} : Create a new etage.
     *
     * @param etage the etage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etage, or with status {@code 400 (Bad Request)} if the etage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etages")
    public ResponseEntity<Etage> createEtage(@RequestBody Etage etage) throws URISyntaxException {
        log.debug("REST request to save Etage : {}", etage);
        if (etage.getId() != null) {
            throw new BadRequestAlertException("A new etage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etage result = etageService.save(etage);
        return ResponseEntity
            .created(new URI("/api/etages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etages/:id} : Updates an existing etage.
     *
     * @param id the id of the etage to save.
     * @param etage the etage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etage,
     * or with status {@code 400 (Bad Request)} if the etage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etages/{id}")
    public ResponseEntity<Etage> updateEtage(@PathVariable(value = "id", required = false) final Long id, @RequestBody Etage etage)
        throws URISyntaxException {
        log.debug("REST request to update Etage : {}, {}", id, etage);
        if (etage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Etage result = etageService.update(etage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, etage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etages/:id} : Partial updates given fields of an existing etage, field will ignore if it is null
     *
     * @param id the id of the etage to save.
     * @param etage the etage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etage,
     * or with status {@code 400 (Bad Request)} if the etage is not valid,
     * or with status {@code 404 (Not Found)} if the etage is not found,
     * or with status {@code 500 (Internal Server Error)} if the etage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Etage> partialUpdateEtage(@PathVariable(value = "id", required = false) final Long id, @RequestBody Etage etage)
        throws URISyntaxException {
        log.debug("REST request to partial update Etage partially : {}, {}", id, etage);
        if (etage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Etage> result = etageService.partialUpdate(etage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, etage.getId().toString())
        );
    }

    /**
     * {@code GET  /etages} : get all the etages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etages in body.
     */
    @GetMapping("/etages")
    public List<Etage> getAllEtages() {
        log.debug("REST request to get all Etages");
        return etageService.findAll();
    }

    /**
     * {@code GET  /etages/:id} : get the "id" etage.
     *
     * @param id the id of the etage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etages/{id}")
    public ResponseEntity<Etage> getEtage(@PathVariable Long id) {
        log.debug("REST request to get Etage : {}", id);
        Optional<Etage> etage = etageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etage);
    }

    /**
     * {@code DELETE  /etages/:id} : delete the "id" etage.
     *
     * @param id the id of the etage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etages/{id}")
    public ResponseEntity<Void> deleteEtage(@PathVariable Long id) {
        log.debug("REST request to delete Etage : {}", id);
        etageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
