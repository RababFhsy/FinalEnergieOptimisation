package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Locale;
import com.feb.optiisationenergie.repository.LocaleRepository;
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
 * Integration tests for the {@link LocaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocaleResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_TYPE_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_LOCAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocaleRepository localeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocaleMockMvc;

    private Locale locale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locale createEntity(EntityManager em) {
        Locale locale = new Locale().numero(DEFAULT_NUMERO).typeLocal(DEFAULT_TYPE_LOCAL);
        return locale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locale createUpdatedEntity(EntityManager em) {
        Locale locale = new Locale().numero(UPDATED_NUMERO).typeLocal(UPDATED_TYPE_LOCAL);
        return locale;
    }

    @BeforeEach
    public void initTest() {
        locale = createEntity(em);
    }

    @Test
    @Transactional
    void createLocale() throws Exception {
        int databaseSizeBeforeCreate = localeRepository.findAll().size();
        // Create the Locale
        restLocaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locale)))
            .andExpect(status().isCreated());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeCreate + 1);
        Locale testLocale = localeList.get(localeList.size() - 1);
        assertThat(testLocale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLocale.getTypeLocal()).isEqualTo(DEFAULT_TYPE_LOCAL);
    }

    @Test
    @Transactional
    void createLocaleWithExistingId() throws Exception {
        // Create the Locale with an existing ID
        locale.setId(1L);

        int databaseSizeBeforeCreate = localeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locale)))
            .andExpect(status().isBadRequest());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLocales() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        // Get all the localeList
        restLocaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locale.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].typeLocal").value(hasItem(DEFAULT_TYPE_LOCAL)));
    }

    @Test
    @Transactional
    void getLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        // Get the locale
        restLocaleMockMvc
            .perform(get(ENTITY_API_URL_ID, locale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locale.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.typeLocal").value(DEFAULT_TYPE_LOCAL));
    }

    @Test
    @Transactional
    void getNonExistingLocale() throws Exception {
        // Get the locale
        restLocaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        int databaseSizeBeforeUpdate = localeRepository.findAll().size();

        // Update the locale
        Locale updatedLocale = localeRepository.findById(locale.getId()).get();
        // Disconnect from session so that the updates on updatedLocale are not directly saved in db
        em.detach(updatedLocale);
        updatedLocale.numero(UPDATED_NUMERO).typeLocal(UPDATED_TYPE_LOCAL);

        restLocaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocale))
            )
            .andExpect(status().isOk());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
        Locale testLocale = localeList.get(localeList.size() - 1);
        assertThat(testLocale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLocale.getTypeLocal()).isEqualTo(UPDATED_TYPE_LOCAL);
    }

    @Test
    @Transactional
    void putNonExistingLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocaleWithPatch() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        int databaseSizeBeforeUpdate = localeRepository.findAll().size();

        // Update the locale using partial update
        Locale partialUpdatedLocale = new Locale();
        partialUpdatedLocale.setId(locale.getId());

        partialUpdatedLocale.typeLocal(UPDATED_TYPE_LOCAL);

        restLocaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocale))
            )
            .andExpect(status().isOk());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
        Locale testLocale = localeList.get(localeList.size() - 1);
        assertThat(testLocale.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLocale.getTypeLocal()).isEqualTo(UPDATED_TYPE_LOCAL);
    }

    @Test
    @Transactional
    void fullUpdateLocaleWithPatch() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        int databaseSizeBeforeUpdate = localeRepository.findAll().size();

        // Update the locale using partial update
        Locale partialUpdatedLocale = new Locale();
        partialUpdatedLocale.setId(locale.getId());

        partialUpdatedLocale.numero(UPDATED_NUMERO).typeLocal(UPDATED_TYPE_LOCAL);

        restLocaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocale))
            )
            .andExpect(status().isOk());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
        Locale testLocale = localeList.get(localeList.size() - 1);
        assertThat(testLocale.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLocale.getTypeLocal()).isEqualTo(UPDATED_TYPE_LOCAL);
    }

    @Test
    @Transactional
    void patchNonExistingLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locale))
            )
            .andExpect(status().isBadRequest());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocale() throws Exception {
        int databaseSizeBeforeUpdate = localeRepository.findAll().size();
        locale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(locale)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Locale in the database
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocale() throws Exception {
        // Initialize the database
        localeRepository.saveAndFlush(locale);

        int databaseSizeBeforeDelete = localeRepository.findAll().size();

        // Delete the locale
        restLocaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, locale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locale> localeList = localeRepository.findAll();
        assertThat(localeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
