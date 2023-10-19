package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Prediction;
import com.feb.optiisationenergie.repository.PredictionRepository;
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
 * Integration tests for the {@link PredictionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PredictionResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CONSOMMATION_PREDIT = 1D;
    private static final Double UPDATED_CONSOMMATION_PREDIT = 2D;

    private static final String DEFAULT_PRECISION = "AAAAAAAAAA";
    private static final String UPDATED_PRECISION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/predictions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPredictionMockMvc;

    private Prediction prediction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prediction createEntity(EntityManager em) {
        Prediction prediction = new Prediction()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .consommationPredit(DEFAULT_CONSOMMATION_PREDIT)
            .precision(DEFAULT_PRECISION);
        return prediction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prediction createUpdatedEntity(EntityManager em) {
        Prediction prediction = new Prediction()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .consommationPredit(UPDATED_CONSOMMATION_PREDIT)
            .precision(UPDATED_PRECISION);
        return prediction;
    }

    @BeforeEach
    public void initTest() {
        prediction = createEntity(em);
    }

    @Test
    @Transactional
    void createPrediction() throws Exception {
        int databaseSizeBeforeCreate = predictionRepository.findAll().size();
        // Create the Prediction
        restPredictionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prediction)))
            .andExpect(status().isCreated());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeCreate + 1);
        Prediction testPrediction = predictionList.get(predictionList.size() - 1);
        assertThat(testPrediction.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPrediction.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPrediction.getConsommationPredit()).isEqualTo(DEFAULT_CONSOMMATION_PREDIT);
        assertThat(testPrediction.getPrecision()).isEqualTo(DEFAULT_PRECISION);
    }

    @Test
    @Transactional
    void createPredictionWithExistingId() throws Exception {
        // Create the Prediction with an existing ID
        prediction.setId(1L);

        int databaseSizeBeforeCreate = predictionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPredictionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prediction)))
            .andExpect(status().isBadRequest());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPredictions() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Get all the predictionList
        restPredictionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prediction.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].consommationPredit").value(hasItem(DEFAULT_CONSOMMATION_PREDIT.doubleValue())))
            .andExpect(jsonPath("$.[*].precision").value(hasItem(DEFAULT_PRECISION)));
    }

    @Test
    @Transactional
    void getPrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        // Get the prediction
        restPredictionMockMvc
            .perform(get(ENTITY_API_URL_ID, prediction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prediction.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.consommationPredit").value(DEFAULT_CONSOMMATION_PREDIT.doubleValue()))
            .andExpect(jsonPath("$.precision").value(DEFAULT_PRECISION));
    }

    @Test
    @Transactional
    void getNonExistingPrediction() throws Exception {
        // Get the prediction
        restPredictionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();

        // Update the prediction
        Prediction updatedPrediction = predictionRepository.findById(prediction.getId()).get();
        // Disconnect from session so that the updates on updatedPrediction are not directly saved in db
        em.detach(updatedPrediction);
        updatedPrediction
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .consommationPredit(UPDATED_CONSOMMATION_PREDIT)
            .precision(UPDATED_PRECISION);

        restPredictionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrediction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrediction))
            )
            .andExpect(status().isOk());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
        Prediction testPrediction = predictionList.get(predictionList.size() - 1);
        assertThat(testPrediction.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPrediction.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPrediction.getConsommationPredit()).isEqualTo(UPDATED_CONSOMMATION_PREDIT);
        assertThat(testPrediction.getPrecision()).isEqualTo(UPDATED_PRECISION);
    }

    @Test
    @Transactional
    void putNonExistingPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prediction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prediction))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prediction))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prediction)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePredictionWithPatch() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();

        // Update the prediction using partial update
        Prediction partialUpdatedPrediction = new Prediction();
        partialUpdatedPrediction.setId(prediction.getId());

        partialUpdatedPrediction.dateDebut(UPDATED_DATE_DEBUT).precision(UPDATED_PRECISION);

        restPredictionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrediction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrediction))
            )
            .andExpect(status().isOk());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
        Prediction testPrediction = predictionList.get(predictionList.size() - 1);
        assertThat(testPrediction.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPrediction.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPrediction.getConsommationPredit()).isEqualTo(DEFAULT_CONSOMMATION_PREDIT);
        assertThat(testPrediction.getPrecision()).isEqualTo(UPDATED_PRECISION);
    }

    @Test
    @Transactional
    void fullUpdatePredictionWithPatch() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();

        // Update the prediction using partial update
        Prediction partialUpdatedPrediction = new Prediction();
        partialUpdatedPrediction.setId(prediction.getId());

        partialUpdatedPrediction
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .consommationPredit(UPDATED_CONSOMMATION_PREDIT)
            .precision(UPDATED_PRECISION);

        restPredictionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrediction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrediction))
            )
            .andExpect(status().isOk());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
        Prediction testPrediction = predictionList.get(predictionList.size() - 1);
        assertThat(testPrediction.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPrediction.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPrediction.getConsommationPredit()).isEqualTo(UPDATED_CONSOMMATION_PREDIT);
        assertThat(testPrediction.getPrecision()).isEqualTo(UPDATED_PRECISION);
    }

    @Test
    @Transactional
    void patchNonExistingPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prediction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prediction))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prediction))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrediction() throws Exception {
        int databaseSizeBeforeUpdate = predictionRepository.findAll().size();
        prediction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPredictionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prediction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prediction in the database
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrediction() throws Exception {
        // Initialize the database
        predictionRepository.saveAndFlush(prediction);

        int databaseSizeBeforeDelete = predictionRepository.findAll().size();

        // Delete the prediction
        restPredictionMockMvc
            .perform(delete(ENTITY_API_URL_ID, prediction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prediction> predictionList = predictionRepository.findAll();
        assertThat(predictionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
