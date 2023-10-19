package com.feb.optiisationenergie.web.rest;

import com.feb.optiisationenergie.domain.Locale;
import com.feb.optiisationenergie.repository.LocaleRepository;
import com.feb.optiisationenergie.service.LocaleService;
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
 * REST controller for managing {@link com.feb.optiisationenergie.domain.Locale}.
 */
@RestController
@RequestMapping("/api")
public class LocaleResource {

    private final Logger log = LoggerFactory.getLogger(LocaleResource.class);

    private static final String ENTITY_NAME = "locale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocaleService localeService;

    private final LocaleRepository localeRepository;

    public LocaleResource(LocaleService localeService, LocaleRepository localeRepository) {
        this.localeService = localeService;
        this.localeRepository = localeRepository;
    }

    /**
     * {@code POST  /locales} : Create a new locale.
     *
     * @param locale the locale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locale, or with status {@code 400 (Bad Request)} if the locale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locales")
    public ResponseEntity<Locale> createLocale(@RequestBody Locale locale) throws URISyntaxException {
        log.debug("REST request to save Locale : {}", locale);
        if (locale.getId() != null) {
            throw new BadRequestAlertException("A new locale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locale result = localeService.save(locale);
        return ResponseEntity
            .created(new URI("/api/locales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locales/:id} : Updates an existing locale.
     *
     * @param id the id of the locale to save.
     * @param locale the locale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locale,
     * or with status {@code 400 (Bad Request)} if the locale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locales/{id}")
    public ResponseEntity<Locale> updateLocale(@PathVariable(value = "id", required = false) final Long id, @RequestBody Locale locale)
        throws URISyntaxException {
        log.debug("REST request to update Locale : {}, {}", id, locale);
        if (locale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Locale result = localeService.update(locale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /locales/:id} : Partial updates given fields of an existing locale, field will ignore if it is null
     *
     * @param id the id of the locale to save.
     * @param locale the locale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locale,
     * or with status {@code 400 (Bad Request)} if the locale is not valid,
     * or with status {@code 404 (Not Found)} if the locale is not found,
     * or with status {@code 500 (Internal Server Error)} if the locale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/locales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locale> partialUpdateLocale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locale locale
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locale partially : {}, {}", id, locale);
        if (locale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!localeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locale> result = localeService.partialUpdate(locale);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locale.getId().toString())
        );
    }

    /**
     * {@code GET  /locales} : get all the locales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locales in body.
     */
    @GetMapping("/locales")
    public List<Locale> getAllLocales() {
        log.debug("REST request to get all Locales");
        return localeService.findAll();
    }

    /**
     * {@code GET  /locales/:id} : get the "id" locale.
     *
     * @param id the id of the locale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locales/{id}")
    public ResponseEntity<Locale> getLocale(@PathVariable Long id) {
        log.debug("REST request to get Locale : {}", id);
        Optional<Locale> locale = localeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(locale);
    }

    /**
     * {@code DELETE  /locales/:id} : delete the "id" locale.
     *
     * @param id the id of the locale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locales/{id}")
    public ResponseEntity<Void> deleteLocale(@PathVariable Long id) {
        log.debug("REST request to delete Locale : {}", id);
        localeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
