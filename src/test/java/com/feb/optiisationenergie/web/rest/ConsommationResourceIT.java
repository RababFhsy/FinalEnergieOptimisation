package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Consommation;
import com.feb.optiisationenergie.repository.ConsommationRepository;
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
 * Integration tests for the {@link ConsommationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsommationResourceIT {

    private static final String DEFAULT_ENERGIE_CONSOMMATION = "AAAAAAAAAA";
    private static final String UPDATED_ENERGIE_CONSOMMATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CONSOMMATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CONSOMMATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/consommations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsommationRepository consommationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsommationMockMvc;

    private Consommation consommation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consommation createEntity(EntityManager em) {
        Consommation consommation = new Consommation()
            .energieConsommation(DEFAULT_ENERGIE_CONSOMMATION)
            .dateConsommation(DEFAULT_DATE_CONSOMMATION);
        return consommation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consommation createUpdatedEntity(EntityManager em) {
        Consommation consommation = new Consommation()
            .energieConsommation(UPDATED_ENERGIE_CONSOMMATION)
            .dateConsommation(UPDATED_DATE_CONSOMMATION);
        return consommation;
    }

    @BeforeEach
    public void initTest() {
        consommation = createEntity(em);
    }

    @Test
    @Transactional
    void createConsommation() throws Exception {
        int databaseSizeBeforeCreate = consommationRepository.findAll().size();
        // Create the Consommation
        restConsommationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consommation)))
            .andExpect(status().isCreated());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeCreate + 1);
        Consommation testConsommation = consommationList.get(consommationList.size() - 1);
        assertThat(testConsommation.getEnergieConsommation()).isEqualTo(DEFAULT_ENERGIE_CONSOMMATION);
        assertThat(testConsommation.getDateConsommation()).isEqualTo(DEFAULT_DATE_CONSOMMATION);
    }

    @Test
    @Transactional
    void createConsommationWithExistingId() throws Exception {
        // Create the Consommation with an existing ID
        consommation.setId(1L);

        int databaseSizeBeforeCreate = consommationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsommationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consommation)))
            .andExpect(status().isBadRequest());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConsommations() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        // Get all the consommationList
        restConsommationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consommation.getId().intValue())))
            .andExpect(jsonPath("$.[*].energieConsommation").value(hasItem(DEFAULT_ENERGIE_CONSOMMATION)))
            .andExpect(jsonPath("$.[*].dateConsommation").value(hasItem(DEFAULT_DATE_CONSOMMATION.toString())));
    }

    @Test
    @Transactional
    void getConsommation() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        // Get the consommation
        restConsommationMockMvc
            .perform(get(ENTITY_API_URL_ID, consommation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consommation.getId().intValue()))
            .andExpect(jsonPath("$.energieConsommation").value(DEFAULT_ENERGIE_CONSOMMATION))
            .andExpect(jsonPath("$.dateConsommation").value(DEFAULT_DATE_CONSOMMATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConsommation() throws Exception {
        // Get the consommation
        restConsommationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConsommation() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();

        // Update the consommation
        Consommation updatedConsommation = consommationRepository.findById(consommation.getId()).get();
        // Disconnect from session so that the updates on updatedConsommation are not directly saved in db
        em.detach(updatedConsommation);
        updatedConsommation.energieConsommation(UPDATED_ENERGIE_CONSOMMATION).dateConsommation(UPDATED_DATE_CONSOMMATION);

        restConsommationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConsommation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConsommation))
            )
            .andExpect(status().isOk());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
        Consommation testConsommation = consommationList.get(consommationList.size() - 1);
        assertThat(testConsommation.getEnergieConsommation()).isEqualTo(UPDATED_ENERGIE_CONSOMMATION);
        assertThat(testConsommation.getDateConsommation()).isEqualTo(UPDATED_DATE_CONSOMMATION);
    }

    @Test
    @Transactional
    void putNonExistingConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consommation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consommation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consommation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consommation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsommationWithPatch() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();

        // Update the consommation using partial update
        Consommation partialUpdatedConsommation = new Consommation();
        partialUpdatedConsommation.setId(consommation.getId());

        restConsommationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsommation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsommation))
            )
            .andExpect(status().isOk());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
        Consommation testConsommation = consommationList.get(consommationList.size() - 1);
        assertThat(testConsommation.getEnergieConsommation()).isEqualTo(DEFAULT_ENERGIE_CONSOMMATION);
        assertThat(testConsommation.getDateConsommation()).isEqualTo(DEFAULT_DATE_CONSOMMATION);
    }

    @Test
    @Transactional
    void fullUpdateConsommationWithPatch() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();

        // Update the consommation using partial update
        Consommation partialUpdatedConsommation = new Consommation();
        partialUpdatedConsommation.setId(consommation.getId());

        partialUpdatedConsommation.energieConsommation(UPDATED_ENERGIE_CONSOMMATION).dateConsommation(UPDATED_DATE_CONSOMMATION);

        restConsommationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsommation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsommation))
            )
            .andExpect(status().isOk());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
        Consommation testConsommation = consommationList.get(consommationList.size() - 1);
        assertThat(testConsommation.getEnergieConsommation()).isEqualTo(UPDATED_ENERGIE_CONSOMMATION);
        assertThat(testConsommation.getDateConsommation()).isEqualTo(UPDATED_DATE_CONSOMMATION);
    }

    @Test
    @Transactional
    void patchNonExistingConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consommation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consommation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consommation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsommation() throws Exception {
        int databaseSizeBeforeUpdate = consommationRepository.findAll().size();
        consommation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsommationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(consommation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Consommation in the database
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsommation() throws Exception {
        // Initialize the database
        consommationRepository.saveAndFlush(consommation);

        int databaseSizeBeforeDelete = consommationRepository.findAll().size();

        // Delete the consommation
        restConsommationMockMvc
            .perform(delete(ENTITY_API_URL_ID, consommation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consommation> consommationList = consommationRepository.findAll();
        assertThat(consommationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
