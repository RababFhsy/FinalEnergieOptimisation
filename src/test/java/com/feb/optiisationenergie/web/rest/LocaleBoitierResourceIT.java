package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.LocaleBoitier;
import com.feb.optiisationenergie.repository.LocaleBoitierRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocaleBoitierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocaleBoitierResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/locale-boitiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocaleBoitierRepository localeBoitierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocaleBoitierMockMvc;

    private LocaleBoitier localeBoitier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocaleBoitier createEntity(EntityManager em) {
        LocaleBoitier localeBoitier = new LocaleBoitier().dateDebut(DEFAULT_DATE_DEBUT).dateFin(DEFAULT_DATE_FIN);
        return localeBoitier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocaleBoitier createUpdatedEntity(EntityManager em) {
        LocaleBoitier localeBoitier = new LocaleBoitier().dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);
        return localeBoitier;
    }

    @BeforeEach
    public void initTest() {
        localeBoitier = createEntity(em);
    }

    @Test
    @Transactional
    void createLocaleBoitier() throws Exception {
        int databaseSizeBeforeCreate = localeBoitierRepository.findAll().size();
        // Create the LocaleBoitier
        restLocaleBoitierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localeBoitier)))
            .andExpect(status().isCreated());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeCreate + 1);
        LocaleBoitier testLocaleBoitier = localeBoitierList.get(localeBoitierList.size() - 1);
        assertThat(testLocaleBoitier.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testLocaleBoitier.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    void createLocaleBoitierWithExistingId() throws Exception {
        // Create the LocaleBoitier with an existing ID
        localeBoitier.setId(1L);

        int databaseSizeBeforeCreate = localeBoitierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaleBoitierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localeBoitier)))
            .andExpect(status().isBadRequest());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocaleBoitiers() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        // Get all the localeBoitierList
        restLocaleBoitierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localeBoitier.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }

    @Test
    @Transactional
    void getLocaleBoitier() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        // Get the localeBoitier
        restLocaleBoitierMockMvc
            .perform(get(ENTITY_API_URL_ID, localeBoitier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localeBoitier.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLocaleBoitier() throws Exception {
        // Get the localeBoitier
        restLocaleBoitierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocaleBoitier() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();

        // Update the localeBoitier
        LocaleBoitier updatedLocaleBoitier = localeBoitierRepository.findById(localeBoitier.getId()).get();
        // Disconnect from session so that the updates on updatedLocaleBoitier are not directly saved in db
        em.detach(updatedLocaleBoitier);
        updatedLocaleBoitier.dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restLocaleBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocaleBoitier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocaleBoitier))
            )
            .andExpect(status().isOk());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
        LocaleBoitier testLocaleBoitier = localeBoitierList.get(localeBoitierList.size() - 1);
        assertThat(testLocaleBoitier.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testLocaleBoitier.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void putNonExistingLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localeBoitier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localeBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localeBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(localeBoitier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocaleBoitierWithPatch() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();

        // Update the localeBoitier using partial update
        LocaleBoitier partialUpdatedLocaleBoitier = new LocaleBoitier();
        partialUpdatedLocaleBoitier.setId(localeBoitier.getId());

        partialUpdatedLocaleBoitier.dateFin(UPDATED_DATE_FIN);

        restLocaleBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocaleBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocaleBoitier))
            )
            .andExpect(status().isOk());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
        LocaleBoitier testLocaleBoitier = localeBoitierList.get(localeBoitierList.size() - 1);
        assertThat(testLocaleBoitier.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testLocaleBoitier.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void fullUpdateLocaleBoitierWithPatch() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();

        // Update the localeBoitier using partial update
        LocaleBoitier partialUpdatedLocaleBoitier = new LocaleBoitier();
        partialUpdatedLocaleBoitier.setId(localeBoitier.getId());

        partialUpdatedLocaleBoitier.dateDebut(UPDATED_DATE_DEBUT).dateFin(UPDATED_DATE_FIN);

        restLocaleBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocaleBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocaleBoitier))
            )
            .andExpect(status().isOk());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
        LocaleBoitier testLocaleBoitier = localeBoitierList.get(localeBoitierList.size() - 1);
        assertThat(testLocaleBoitier.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testLocaleBoitier.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, localeBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localeBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localeBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocaleBoitier() throws Exception {
        int databaseSizeBeforeUpdate = localeBoitierRepository.findAll().size();
        localeBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(localeBoitier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocaleBoitier in the database
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocaleBoitier() throws Exception {
        // Initialize the database
        localeBoitierRepository.saveAndFlush(localeBoitier);

        int databaseSizeBeforeDelete = localeBoitierRepository.findAll().size();

        // Delete the localeBoitier
        restLocaleBoitierMockMvc
            .perform(delete(ENTITY_API_URL_ID, localeBoitier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocaleBoitier> localeBoitierList = localeBoitierRepository.findAll();
        assertThat(localeBoitierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
