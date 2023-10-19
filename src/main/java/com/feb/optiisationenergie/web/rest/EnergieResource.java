package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.Energie;
import com.feb.optiisationenergie.repository.EnergieRepository;
import com.feb.optiisationenergie.service.EnergieService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.Energie}.
 */
@RestController
@RequestMapping("/api")
public class EnergieResource {

    private final Logger log = LoggerFactory.getLogger(EnergieResource.class);

    private static final String ENTITY_NAME = "energie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnergieService energieService;

    private final EnergieRepository energieRepository;

    public EnergieResource(EnergieService energieService, EnergieRepository energieRepository) {
        this.energieService = energieService;
        this.energieRepository = energieRepository;
    }

    /**
     * {@code POST  /energies} : Create a new energie.
     *
     * @param energie the energie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new energie, or with status {@code 400 (Bad Request)} if the energie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/energies")
    public ResponseEntity<Energie> createEnergie(@RequestBody Energie energie) throws URISyntaxException {
        log.debug("REST request to save Energie : {}", energie);
        if (energie.getId() != null) {
            throw new BadRequestAlertException("A new energie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Energie result = energieService.save(energie);
        return ResponseEntity
            .created(new URI("/api/energies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /energies/:id} : Updates an existing energie.
     *
     * @param id the id of the energie to save.
     * @param energie the energie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated energie,
     * or with status {@code 400 (Bad Request)} if the energie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the energie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/energies/{id}")
    public ResponseEntity<Energie> updateEnergie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Energie energie)
        throws URISyntaxException {
        log.debug("REST request to update Energie : {}, {}", id, energie);
        if (energie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, energie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!energieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Energie result = energieService.update(energie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, energie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /energies/:id} : Partial updates given fields of an existing energie, field will ignore if it is null
     *
     * @param id the id of the energie to save.
     * @param energie the energie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated energie,
     * or with status {@code 400 (Bad Request)} if the energie is not valid,
     * or with status {@code 404 (Not Found)} if the energie is not found,
     * or with status {@code 500 (Internal Server Error)} if the energie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/energies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Energie> partialUpdateEnergie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Energie energie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Energie partially : {}, {}", id, energie);
        if (energie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, energie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!energieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Energie> result = energieService.partialUpdate(energie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, energie.getId().toString())
        );
    }

    /**
     * {@code GET  /energies} : get all the energies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of energies in body.
     */
    @GetMapping("/energies")
    public List<Energie> getAllEnergies() {
        log.debug("REST request to get all Energies");
        return energieService.findAll();
    }

    /**
     * {@code GET  /energies/:id} : get the "id" energie.
     *
     * @param id the id of the energie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the energie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/energies/{id}")
    public ResponseEntity<Energie> getEnergie(@PathVariable Long id) {
        log.debug("REST request to get Energie : {}", id);
        Optional<Energie> energie = energieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(energie);
    }

    /**
     * {@code DELETE  /energies/:id} : delete the "id" energie.
     *
     * @param id the id of the energie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/energies/{id}")
    public ResponseEntity<Void> deleteEnergie(@PathVariable Long id) {
        log.debug("REST request to delete Energie : {}", id);
        energieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
