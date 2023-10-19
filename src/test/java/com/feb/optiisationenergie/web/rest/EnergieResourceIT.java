package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Energie;
import com.feb.optiisationenergie.repository.EnergieRepository;
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
 * Integration tests for the {@link EnergieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnergieResourceIT {

    private static final String DEFAULT_NOM_SYSTEM_ENERGITIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SYSTEM_ENERGITIQUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/energies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnergieRepository energieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnergieMockMvc;

    private Energie energie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Energie createEntity(EntityManager em) {
        Energie energie = new Energie().nomSystemEnergitique(DEFAULT_NOM_SYSTEM_ENERGITIQUE);
        return energie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Energie createUpdatedEntity(EntityManager em) {
        Energie energie = new Energie().nomSystemEnergitique(UPDATED_NOM_SYSTEM_ENERGITIQUE);
        return energie;
    }

    @BeforeEach
    public void initTest() {
        energie = createEntity(em);
    }

    @Test
    @Transactional
    void createEnergie() throws Exception {
        int databaseSizeBeforeCreate = energieRepository.findAll().size();
        // Create the Energie
        restEnergieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(energie)))
            .andExpect(status().isCreated());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeCreate + 1);
        Energie testEnergie = energieList.get(energieList.size() - 1);
        assertThat(testEnergie.getNomSystemEnergitique()).isEqualTo(DEFAULT_NOM_SYSTEM_ENERGITIQUE);
    }

    @Test
    @Transactional
    void createEnergieWithExistingId() throws Exception {
        // Create the Energie with an existing ID
        energie.setId(1L);

        int databaseSizeBeforeCreate = energieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnergieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(energie)))
            .andExpect(status().isBadRequest());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnergies() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        // Get all the energieList
        restEnergieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(energie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSystemEnergitique").value(hasItem(DEFAULT_NOM_SYSTEM_ENERGITIQUE)));
    }

    @Test
    @Transactional
    void getEnergie() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        // Get the energie
        restEnergieMockMvc
            .perform(get(ENTITY_API_URL_ID, energie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(energie.getId().intValue()))
            .andExpect(jsonPath("$.nomSystemEnergitique").value(DEFAULT_NOM_SYSTEM_ENERGITIQUE));
    }

    @Test
    @Transactional
    void getNonExistingEnergie() throws Exception {
        // Get the energie
        restEnergieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnergie() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        int databaseSizeBeforeUpdate = energieRepository.findAll().size();

        // Update the energie
        Energie updatedEnergie = energieRepository.findById(energie.getId()).get();
        // Disconnect from session so that the updates on updatedEnergie are not directly saved in db
        em.detach(updatedEnergie);
        updatedEnergie.nomSystemEnergitique(UPDATED_NOM_SYSTEM_ENERGITIQUE);

        restEnergieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnergie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnergie))
            )
            .andExpect(status().isOk());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
        Energie testEnergie = energieList.get(energieList.size() - 1);
        assertThat(testEnergie.getNomSystemEnergitique()).isEqualTo(UPDATED_NOM_SYSTEM_ENERGITIQUE);
    }

    @Test
    @Transactional
    void putNonExistingEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, energie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(energie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(energie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(energie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnergieWithPatch() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        int databaseSizeBeforeUpdate = energieRepository.findAll().size();

        // Update the energie using partial update
        Energie partialUpdatedEnergie = new Energie();
        partialUpdatedEnergie.setId(energie.getId());

        partialUpdatedEnergie.nomSystemEnergitique(UPDATED_NOM_SYSTEM_ENERGITIQUE);

        restEnergieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnergie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnergie))
            )
            .andExpect(status().isOk());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
        Energie testEnergie = energieList.get(energieList.size() - 1);
        assertThat(testEnergie.getNomSystemEnergitique()).isEqualTo(UPDATED_NOM_SYSTEM_ENERGITIQUE);
    }

    @Test
    @Transactional
    void fullUpdateEnergieWithPatch() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        int databaseSizeBeforeUpdate = energieRepository.findAll().size();

        // Update the energie using partial update
        Energie partialUpdatedEnergie = new Energie();
        partialUpdatedEnergie.setId(energie.getId());

        partialUpdatedEnergie.nomSystemEnergitique(UPDATED_NOM_SYSTEM_ENERGITIQUE);

        restEnergieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnergie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnergie))
            )
            .andExpect(status().isOk());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
        Energie testEnergie = energieList.get(energieList.size() - 1);
        assertThat(testEnergie.getNomSystemEnergitique()).isEqualTo(UPDATED_NOM_SYSTEM_ENERGITIQUE);
    }

    @Test
    @Transactional
    void patchNonExistingEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, energie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(energie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(energie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnergie() throws Exception {
        int databaseSizeBeforeUpdate = energieRepository.findAll().size();
        energie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnergieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(energie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Energie in the database
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnergie() throws Exception {
        // Initialize the database
        energieRepository.saveAndFlush(energie);

        int databaseSizeBeforeDelete = energieRepository.findAll().size();

        // Delete the energie
        restEnergieMockMvc
            .perform(delete(ENTITY_API_URL_ID, energie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Energie> energieList = energieRepository.findAll();
        assertThat(energieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
