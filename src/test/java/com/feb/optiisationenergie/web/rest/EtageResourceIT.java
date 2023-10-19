package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Etage;
import com.feb.optiisationenergie.repository.EtageRepository;
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
 * Integration tests for the {@link EtageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtageResourceIT {

    private static final Integer DEFAULT_ETAGE_NUMERO = 1;
    private static final Integer UPDATED_ETAGE_NUMERO = 2;

    private static final String ENTITY_API_URL = "/api/etages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtageRepository etageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtageMockMvc;

    private Etage etage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etage createEntity(EntityManager em) {
        Etage etage = new Etage().etageNumero(DEFAULT_ETAGE_NUMERO);
        return etage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etage createUpdatedEntity(EntityManager em) {
        Etage etage = new Etage().etageNumero(UPDATED_ETAGE_NUMERO);
        return etage;
    }

    @BeforeEach
    public void initTest() {
        etage = createEntity(em);
    }

    @Test
    @Transactional
    void createEtage() throws Exception {
        int databaseSizeBeforeCreate = etageRepository.findAll().size();
        // Create the Etage
        restEtageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etage)))
            .andExpect(status().isCreated());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeCreate + 1);
        Etage testEtage = etageList.get(etageList.size() - 1);
        assertThat(testEtage.getEtageNumero()).isEqualTo(DEFAULT_ETAGE_NUMERO);
    }

    @Test
    @Transactional
    void createEtageWithExistingId() throws Exception {
        // Create the Etage with an existing ID
        etage.setId(1L);

        int databaseSizeBeforeCreate = etageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etage)))
            .andExpect(status().isBadRequest());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtages() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        // Get all the etageList
        restEtageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etage.getId().intValue())))
            .andExpect(jsonPath("$.[*].etageNumero").value(hasItem(DEFAULT_ETAGE_NUMERO)));
    }

    @Test
    @Transactional
    void getEtage() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        // Get the etage
        restEtageMockMvc
            .perform(get(ENTITY_API_URL_ID, etage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etage.getId().intValue()))
            .andExpect(jsonPath("$.etageNumero").value(DEFAULT_ETAGE_NUMERO));
    }

    @Test
    @Transactional
    void getNonExistingEtage() throws Exception {
        // Get the etage
        restEtageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtage() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        int databaseSizeBeforeUpdate = etageRepository.findAll().size();

        // Update the etage
        Etage updatedEtage = etageRepository.findById(etage.getId()).get();
        // Disconnect from session so that the updates on updatedEtage are not directly saved in db
        em.detach(updatedEtage);
        updatedEtage.etageNumero(UPDATED_ETAGE_NUMERO);

        restEtageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtage))
            )
            .andExpect(status().isOk());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
        Etage testEtage = etageList.get(etageList.size() - 1);
        assertThat(testEtage.getEtageNumero()).isEqualTo(UPDATED_ETAGE_NUMERO);
    }

    @Test
    @Transactional
    void putNonExistingEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtageWithPatch() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        int databaseSizeBeforeUpdate = etageRepository.findAll().size();

        // Update the etage using partial update
        Etage partialUpdatedEtage = new Etage();
        partialUpdatedEtage.setId(etage.getId());

        restEtageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtage))
            )
            .andExpect(status().isOk());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
        Etage testEtage = etageList.get(etageList.size() - 1);
        assertThat(testEtage.getEtageNumero()).isEqualTo(DEFAULT_ETAGE_NUMERO);
    }

    @Test
    @Transactional
    void fullUpdateEtageWithPatch() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        int databaseSizeBeforeUpdate = etageRepository.findAll().size();

        // Update the etage using partial update
        Etage partialUpdatedEtage = new Etage();
        partialUpdatedEtage.setId(etage.getId());

        partialUpdatedEtage.etageNumero(UPDATED_ETAGE_NUMERO);

        restEtageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtage))
            )
            .andExpect(status().isOk());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
        Etage testEtage = etageList.get(etageList.size() - 1);
        assertThat(testEtage.getEtageNumero()).isEqualTo(UPDATED_ETAGE_NUMERO);
    }

    @Test
    @Transactional
    void patchNonExistingEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtage() throws Exception {
        int databaseSizeBeforeUpdate = etageRepository.findAll().size();
        etage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(etage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Etage in the database
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtage() throws Exception {
        // Initialize the database
        etageRepository.saveAndFlush(etage);

        int databaseSizeBeforeDelete = etageRepository.findAll().size();

        // Delete the etage
        restEtageMockMvc
            .perform(delete(ENTITY_API_URL_ID, etage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etage> etageList = etageRepository.findAll();
        assertThat(etageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
