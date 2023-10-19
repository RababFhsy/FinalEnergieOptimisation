package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Preference;
import com.feb.optiisationenergie.repository.PreferenceRepository;
import com.feb.optiisationenergie.service.PreferenceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Preference}.
 */
@Service
@Transactional
public class PreferenceServiceImpl implements PreferenceService {

    private final Logger log = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    private final PreferenceRepository preferenceRepository;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public Preference save(Preference preference) {
        log.debug("Request to save Preference : {}", preference);
        return preferenceRepository.save(preference);
    }

    @Override
    public Preference update(Preference preference) {
        log.debug("Request to update Preference : {}", preference);
        return preferenceRepository.save(preference);
    }

    @Override
    public Optional<Preference> partialUpdate(Preference preference) {
        log.debug("Request to partially update Preference : {}", preference);

        return preferenceRepository
            .findById(preference.getId())
            .map(existingPreference -> {
                if (preference.getTempMinValue() != null) {
                    existingPreference.setTempMinValue(preference.getTempMinValue());
                }
                if (preference.getTempMaxValue() != null) {
                    existingPreference.setTempMaxValue(preference.getTempMaxValue());
                }
                if (preference.getPlageHoraire() != null) {
                    existingPreference.setPlageHoraire(preference.getPlageHoraire());
                }

                return existingPreference;
            })
            .map(preferenceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preference> findAll() {
        log.debug("Request to get all Preferences");
        return preferenceRepository.findAll();
    }

    public Page<Preference> findAllWithEagerRelationships(Pageable pageable) {
        return preferenceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Preference> findOne(Long id) {
        log.debug("Request to get Preference : {}", id);
        return preferenceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preference : {}", id);
        preferenceRepository.deleteById(id);
    }
}
