package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.CapteurBoitier;
import com.feb.optiisationenergie.repository.CapteurBoitierRepository;
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
 * Integration tests for the {@link CapteurBoitierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CapteurBoitierResourceIT {

    private static final String DEFAULT_BRANCHE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCHE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/capteur-boitiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CapteurBoitierRepository capteurBoitierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCapteurBoitierMockMvc;

    private CapteurBoitier capteurBoitier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CapteurBoitier createEntity(EntityManager em) {
        CapteurBoitier capteurBoitier = new CapteurBoitier().branche(DEFAULT_BRANCHE);
        return capteurBoitier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CapteurBoitier createUpdatedEntity(EntityManager em) {
        CapteurBoitier capteurBoitier = new CapteurBoitier().branche(UPDATED_BRANCHE);
        return capteurBoitier;
    }

    @BeforeEach
    public void initTest() {
        capteurBoitier = createEntity(em);
    }

    @Test
    @Transactional
    void createCapteurBoitier() throws Exception {
        int databaseSizeBeforeCreate = capteurBoitierRepository.findAll().size();
        // Create the CapteurBoitier
        restCapteurBoitierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isCreated());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeCreate + 1);
        CapteurBoitier testCapteurBoitier = capteurBoitierList.get(capteurBoitierList.size() - 1);
        assertThat(testCapteurBoitier.getBranche()).isEqualTo(DEFAULT_BRANCHE);
    }

    @Test
    @Transactional
    void createCapteurBoitierWithExistingId() throws Exception {
        // Create the CapteurBoitier with an existing ID
        capteurBoitier.setId(1L);

        int databaseSizeBeforeCreate = capteurBoitierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapteurBoitierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCapteurBoitiers() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        // Get all the capteurBoitierList
        restCapteurBoitierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capteurBoitier.getId().intValue())))
            .andExpect(jsonPath("$.[*].branche").value(hasItem(DEFAULT_BRANCHE)));
    }

    @Test
    @Transactional
    void getCapteurBoitier() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        // Get the capteurBoitier
        restCapteurBoitierMockMvc
            .perform(get(ENTITY_API_URL_ID, capteurBoitier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(capteurBoitier.getId().intValue()))
            .andExpect(jsonPath("$.branche").value(DEFAULT_BRANCHE));
    }

    @Test
    @Transactional
    void getNonExistingCapteurBoitier() throws Exception {
        // Get the capteurBoitier
        restCapteurBoitierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCapteurBoitier() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();

        // Update the capteurBoitier
        CapteurBoitier updatedCapteurBoitier = capteurBoitierRepository.findById(capteurBoitier.getId()).get();
        // Disconnect from session so that the updates on updatedCapteurBoitier are not directly saved in db
        em.detach(updatedCapteurBoitier);
        updatedCapteurBoitier.branche(UPDATED_BRANCHE);

        restCapteurBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCapteurBoitier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCapteurBoitier))
            )
            .andExpect(status().isOk());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
        CapteurBoitier testCapteurBoitier = capteurBoitierList.get(capteurBoitierList.size() - 1);
        assertThat(testCapteurBoitier.getBranche()).isEqualTo(UPDATED_BRANCHE);
    }

    @Test
    @Transactional
    void putNonExistingCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, capteurBoitier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capteurBoitier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCapteurBoitierWithPatch() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();

        // Update the capteurBoitier using partial update
        CapteurBoitier partialUpdatedCapteurBoitier = new CapteurBoitier();
        partialUpdatedCapteurBoitier.setId(capteurBoitier.getId());

        partialUpdatedCapteurBoitier.branche(UPDATED_BRANCHE);

        restCapteurBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapteurBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapteurBoitier))
            )
            .andExpect(status().isOk());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
        CapteurBoitier testCapteurBoitier = capteurBoitierList.get(capteurBoitierList.size() - 1);
        assertThat(testCapteurBoitier.getBranche()).isEqualTo(UPDATED_BRANCHE);
    }

    @Test
    @Transactional
    void fullUpdateCapteurBoitierWithPatch() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();

        // Update the capteurBoitier using partial update
        CapteurBoitier partialUpdatedCapteurBoitier = new CapteurBoitier();
        partialUpdatedCapteurBoitier.setId(capteurBoitier.getId());

        partialUpdatedCapteurBoitier.branche(UPDATED_BRANCHE);

        restCapteurBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapteurBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapteurBoitier))
            )
            .andExpect(status().isOk());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
        CapteurBoitier testCapteurBoitier = capteurBoitierList.get(capteurBoitierList.size() - 1);
        assertThat(testCapteurBoitier.getBranche()).isEqualTo(UPDATED_BRANCHE);
    }

    @Test
    @Transactional
    void patchNonExistingCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, capteurBoitier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCapteurBoitier() throws Exception {
        int databaseSizeBeforeUpdate = capteurBoitierRepository.findAll().size();
        capteurBoitier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapteurBoitierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(capteurBoitier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CapteurBoitier in the database
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCapteurBoitier() throws Exception {
        // Initialize the database
        capteurBoitierRepository.saveAndFlush(capteurBoitier);

        int databaseSizeBeforeDelete = capteurBoitierRepository.findAll().size();

        // Delete the capteurBoitier
        restCapteurBoitierMockMvc
            .perform(delete(ENTITY_API_URL_ID, capteurBoitier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CapteurBoitier> capteurBoitierList = capteurBoitierRepository.findAll();
        assertThat(capteurBoitierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
