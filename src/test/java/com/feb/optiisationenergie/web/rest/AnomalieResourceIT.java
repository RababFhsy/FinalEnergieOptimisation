package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Anomalie;
import com.feb.optiisationenergie.repository.AnomalieRepository;
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
 * Integration tests for the {@link AnomalieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnomalieResourceIT {

    private static final Double DEFAULT_ZONE_NORMALE_MIN = 1D;
    private static final Double UPDATED_ZONE_NORMALE_MIN = 2D;

    private static final Double DEFAULT_ZONE_NORMALE_MAX = 1D;
    private static final Double UPDATED_ZONE_NORMALE_MAX = 2D;

    private static final LocalDate DEFAULT_DATE_ANOMALIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ANOMALIE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION_ANOMALIE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_ANOMALIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anomalies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnomalieRepository anomalieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnomalieMockMvc;

    private Anomalie anomalie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anomalie createEntity(EntityManager em) {
        Anomalie anomalie = new Anomalie()
            .zoneNormaleMin(DEFAULT_ZONE_NORMALE_MIN)
            .zoneNormaleMax(DEFAULT_ZONE_NORMALE_MAX)
            .dateAnomalie(DEFAULT_DATE_ANOMALIE)
            .descriptionAnomalie(DEFAULT_DESCRIPTION_ANOMALIE);
        return anomalie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anomalie createUpdatedEntity(EntityManager em) {
        Anomalie anomalie = new Anomalie()
            .zoneNormaleMin(UPDATED_ZONE_NORMALE_MIN)
            .zoneNormaleMax(UPDATED_ZONE_NORMALE_MAX)
            .dateAnomalie(UPDATED_DATE_ANOMALIE)
            .descriptionAnomalie(UPDATED_DESCRIPTION_ANOMALIE);
        return anomalie;
    }

    @BeforeEach
    public void initTest() {
        anomalie = createEntity(em);
    }

    @Test
    @Transactional
    void createAnomalie() throws Exception {
        int databaseSizeBeforeCreate = anomalieRepository.findAll().size();
        // Create the Anomalie
        restAnomalieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anomalie)))
            .andExpect(status().isCreated());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeCreate + 1);
        Anomalie testAnomalie = anomalieList.get(anomalieList.size() - 1);
        assertThat(testAnomalie.getZoneNormaleMin()).isEqualTo(DEFAULT_ZONE_NORMALE_MIN);
        assertThat(testAnomalie.getZoneNormaleMax()).isEqualTo(DEFAULT_ZONE_NORMALE_MAX);
        assertThat(testAnomalie.getDateAnomalie()).isEqualTo(DEFAULT_DATE_ANOMALIE);
        assertThat(testAnomalie.getDescriptionAnomalie()).isEqualTo(DEFAULT_DESCRIPTION_ANOMALIE);
    }

    @Test
    @Transactional
    void createAnomalieWithExistingId() throws Exception {
        // Create the Anomalie with an existing ID
        anomalie.setId(1L);

        int databaseSizeBeforeCreate = anomalieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnomalieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anomalie)))
            .andExpect(status().isBadRequest());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnomalies() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        // Get all the anomalieList
        restAnomalieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomalie.getId().intValue())))
            .andExpect(jsonPath("$.[*].zoneNormaleMin").value(hasItem(DEFAULT_ZONE_NORMALE_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].zoneNormaleMax").value(hasItem(DEFAULT_ZONE_NORMALE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].dateAnomalie").value(hasItem(DEFAULT_DATE_ANOMALIE.toString())))
            .andExpect(jsonPath("$.[*].descriptionAnomalie").value(hasItem(DEFAULT_DESCRIPTION_ANOMALIE)));
    }

    @Test
    @Transactional
    void getAnomalie() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        // Get the anomalie
        restAnomalieMockMvc
            .perform(get(ENTITY_API_URL_ID, anomalie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anomalie.getId().intValue()))
            .andExpect(jsonPath("$.zoneNormaleMin").value(DEFAULT_ZONE_NORMALE_MIN.doubleValue()))
            .andExpect(jsonPath("$.zoneNormaleMax").value(DEFAULT_ZONE_NORMALE_MAX.doubleValue()))
            .andExpect(jsonPath("$.dateAnomalie").value(DEFAULT_DATE_ANOMALIE.toString()))
            .andExpect(jsonPath("$.descriptionAnomalie").value(DEFAULT_DESCRIPTION_ANOMALIE));
    }

    @Test
    @Transactional
    void getNonExistingAnomalie() throws Exception {
        // Get the anomalie
        restAnomalieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnomalie() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();

        // Update the anomalie
        Anomalie updatedAnomalie = anomalieRepository.findById(anomalie.getId()).get();
        // Disconnect from session so that the updates on updatedAnomalie are not directly saved in db
        em.detach(updatedAnomalie);
        updatedAnomalie
            .zoneNormaleMin(UPDATED_ZONE_NORMALE_MIN)
            .zoneNormaleMax(UPDATED_ZONE_NORMALE_MAX)
            .dateAnomalie(UPDATED_DATE_ANOMALIE)
            .descriptionAnomalie(UPDATED_DESCRIPTION_ANOMALIE);

        restAnomalieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnomalie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnomalie))
            )
            .andExpect(status().isOk());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
        Anomalie testAnomalie = anomalieList.get(anomalieList.size() - 1);
        assertThat(testAnomalie.getZoneNormaleMin()).isEqualTo(UPDATED_ZONE_NORMALE_MIN);
        assertThat(testAnomalie.getZoneNormaleMax()).isEqualTo(UPDATED_ZONE_NORMALE_MAX);
        assertThat(testAnomalie.getDateAnomalie()).isEqualTo(UPDATED_DATE_ANOMALIE);
        assertThat(testAnomalie.getDescriptionAnomalie()).isEqualTo(UPDATED_DESCRIPTION_ANOMALIE);
    }

    @Test
    @Transactional
    void putNonExistingAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anomalie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anomalie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anomalie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anomalie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnomalieWithPatch() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();

        // Update the anomalie using partial update
        Anomalie partialUpdatedAnomalie = new Anomalie();
        partialUpdatedAnomalie.setId(anomalie.getId());

        partialUpdatedAnomalie.dateAnomalie(UPDATED_DATE_ANOMALIE).descriptionAnomalie(UPDATED_DESCRIPTION_ANOMALIE);

        restAnomalieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnomalie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnomalie))
            )
            .andExpect(status().isOk());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
        Anomalie testAnomalie = anomalieList.get(anomalieList.size() - 1);
        assertThat(testAnomalie.getZoneNormaleMin()).isEqualTo(DEFAULT_ZONE_NORMALE_MIN);
        assertThat(testAnomalie.getZoneNormaleMax()).isEqualTo(DEFAULT_ZONE_NORMALE_MAX);
        assertThat(testAnomalie.getDateAnomalie()).isEqualTo(UPDATED_DATE_ANOMALIE);
        assertThat(testAnomalie.getDescriptionAnomalie()).isEqualTo(UPDATED_DESCRIPTION_ANOMALIE);
    }

    @Test
    @Transactional
    void fullUpdateAnomalieWithPatch() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();

        // Update the anomalie using partial update
        Anomalie partialUpdatedAnomalie = new Anomalie();
        partialUpdatedAnomalie.setId(anomalie.getId());

        partialUpdatedAnomalie
            .zoneNormaleMin(UPDATED_ZONE_NORMALE_MIN)
            .zoneNormaleMax(UPDATED_ZONE_NORMALE_MAX)
            .dateAnomalie(UPDATED_DATE_ANOMALIE)
            .descriptionAnomalie(UPDATED_DESCRIPTION_ANOMALIE);

        restAnomalieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnomalie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnomalie))
            )
            .andExpect(status().isOk());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
        Anomalie testAnomalie = anomalieList.get(anomalieList.size() - 1);
        assertThat(testAnomalie.getZoneNormaleMin()).isEqualTo(UPDATED_ZONE_NORMALE_MIN);
        assertThat(testAnomalie.getZoneNormaleMax()).isEqualTo(UPDATED_ZONE_NORMALE_MAX);
        assertThat(testAnomalie.getDateAnomalie()).isEqualTo(UPDATED_DATE_ANOMALIE);
        assertThat(testAnomalie.getDescriptionAnomalie()).isEqualTo(UPDATED_DESCRIPTION_ANOMALIE);
    }

    @Test
    @Transactional
    void patchNonExistingAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anomalie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anomalie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anomalie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnomalie() throws Exception {
        int databaseSizeBeforeUpdate = anomalieRepository.findAll().size();
        anomalie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnomalieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anomalie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anomalie in the database
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnomalie() throws Exception {
        // Initialize the database
        anomalieRepository.saveAndFlush(anomalie);

        int databaseSizeBeforeDelete = anomalieRepository.findAll().size();

        // Delete the anomalie
        restAnomalieMockMvc
            .perform(delete(ENTITY_API_URL_ID, anomalie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anomalie> anomalieList = anomalieRepository.findAll();
        assertThat(anomalieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
