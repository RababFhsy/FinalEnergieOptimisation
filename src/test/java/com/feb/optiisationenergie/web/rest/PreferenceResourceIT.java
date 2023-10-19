package com.feb.optiisationenergie.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.feb.optiisationenergie.IntegrationTest;
import com.feb.optiisationenergie.domain.Preference;
import com.feb.optiisationenergie.repository.PreferenceRepository;
import com.feb.optiisationenergie.service.PreferenceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PreferenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PreferenceResourceIT {

    private static final Double DEFAULT_TEMP_MIN_VALUE = 1D;
    private static final Double UPDATED_TEMP_MIN_VALUE = 2D;

    private static final Double DEFAULT_TEMP_MAX_VALUE = 1D;
    private static final Double UPDATED_TEMP_MAX_VALUE = 2D;

    private static final Double DEFAULT_PLAGE_HORAIRE = 1D;
    private static final Double UPDATED_PLAGE_HORAIRE = 2D;

    private static final String ENTITY_API_URL = "/api/preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Mock
    private PreferenceRepository preferenceRepositoryMock;

    @Mock
    private PreferenceService preferenceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferenceMockMvc;

    private Preference preference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createEntity(EntityManager em) {
        Preference preference = new Preference()
            .tempMinValue(DEFAULT_TEMP_MIN_VALUE)
            .tempMaxValue(DEFAULT_TEMP_MAX_VALUE)
            .plageHoraire(DEFAULT_PLAGE_HORAIRE);
        return preference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createUpdatedEntity(EntityManager em) {
        Preference preference = new Preference()
            .tempMinValue(UPDATED_TEMP_MIN_VALUE)
            .tempMaxValue(UPDATED_TEMP_MAX_VALUE)
            .plageHoraire(UPDATED_PLAGE_HORAIRE);
        return preference;
    }

    @BeforeEach
    public void initTest() {
        preference = createEntity(em);
    }

    @Test
    @Transactional
    void createPreference() throws Exception {
        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();
        // Create the Preference
        restPreferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preference)))
            .andExpect(status().isCreated());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.getTempMinValue()).isEqualTo(DEFAULT_TEMP_MIN_VALUE);
        assertThat(testPreference.getTempMaxValue()).isEqualTo(DEFAULT_TEMP_MAX_VALUE);
        assertThat(testPreference.getPlageHoraire()).isEqualTo(DEFAULT_PLAGE_HORAIRE);
    }

    @Test
    @Transactional
    void createPreferenceWithExistingId() throws Exception {
        // Create the Preference with an existing ID
        preference.setId(1L);

        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preference)))
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPreferences() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList
        restPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preference.getId().intValue())))
            .andExpect(jsonPath("$.[*].tempMinValue").value(hasItem(DEFAULT_TEMP_MIN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].tempMaxValue").value(hasItem(DEFAULT_TEMP_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].plageHoraire").value(hasItem(DEFAULT_PLAGE_HORAIRE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPreferencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(preferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreferenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(preferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPreferencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(preferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreferenceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(preferenceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get the preference
        restPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, preference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preference.getId().intValue()))
            .andExpect(jsonPath("$.tempMinValue").value(DEFAULT_TEMP_MIN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.tempMaxValue").value(DEFAULT_TEMP_MAX_VALUE.doubleValue()))
            .andExpect(jsonPath("$.plageHoraire").value(DEFAULT_PLAGE_HORAIRE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPreference() throws Exception {
        // Get the preference
        restPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference
        Preference updatedPreference = preferenceRepository.findById(preference.getId()).get();
        // Disconnect from session so that the updates on updatedPreference are not directly saved in db
        em.detach(updatedPreference);
        updatedPreference.tempMinValue(UPDATED_TEMP_MIN_VALUE).tempMaxValue(UPDATED_TEMP_MAX_VALUE).plageHoraire(UPDATED_PLAGE_HORAIRE);

        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreference))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.getTempMinValue()).isEqualTo(UPDATED_TEMP_MIN_VALUE);
        assertThat(testPreference.getTempMaxValue()).isEqualTo(UPDATED_TEMP_MAX_VALUE);
        assertThat(testPreference.getPlageHoraire()).isEqualTo(UPDATED_PLAGE_HORAIRE);
    }

    @Test
    @Transactional
    void putNonExistingPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preference)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferenceWithPatch() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference using partial update
        Preference partialUpdatedPreference = new Preference();
        partialUpdatedPreference.setId(preference.getId());

        partialUpdatedPreference.tempMaxValue(UPDATED_TEMP_MAX_VALUE);

        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreference))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.getTempMinValue()).isEqualTo(DEFAULT_TEMP_MIN_VALUE);
        assertThat(testPreference.getTempMaxValue()).isEqualTo(UPDATED_TEMP_MAX_VALUE);
        assertThat(testPreference.getPlageHoraire()).isEqualTo(DEFAULT_PLAGE_HORAIRE);
    }

    @Test
    @Transactional
    void fullUpdatePreferenceWithPatch() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference using partial update
        Preference partialUpdatedPreference = new Preference();
        partialUpdatedPreference.setId(preference.getId());

        partialUpdatedPreference
            .tempMinValue(UPDATED_TEMP_MIN_VALUE)
            .tempMaxValue(UPDATED_TEMP_MAX_VALUE)
            .plageHoraire(UPDATED_PLAGE_HORAIRE);

        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreference))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.getTempMinValue()).isEqualTo(UPDATED_TEMP_MIN_VALUE);
        assertThat(testPreference.getTempMaxValue()).isEqualTo(UPDATED_TEMP_MAX_VALUE);
        assertThat(testPreference.getPlageHoraire()).isEqualTo(UPDATED_PLAGE_HORAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(preference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeDelete = preferenceRepository.findAll().size();

        // Delete the preference
        restPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, preference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
