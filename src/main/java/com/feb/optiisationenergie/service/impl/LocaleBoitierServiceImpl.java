package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.LocaleBoitier;
import com.feb.optiisationenergie.repository.LocaleBoitierRepository;
import com.feb.optiisationenergie.service.LocaleBoitierService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LocaleBoitier}.
 */
@Service
@Transactional
public class LocaleBoitierServiceImpl implements LocaleBoitierService {

    private final Logger log = LoggerFactory.getLogger(LocaleBoitierServiceImpl.class);

    private final LocaleBoitierRepository localeBoitierRepository;

    public LocaleBoitierServiceImpl(LocaleBoitierRepository localeBoitierRepository) {
        this.localeBoitierRepository = localeBoitierRepository;
    }

    @Override
    public LocaleBoitier save(LocaleBoitier localeBoitier) {
        log.debug("Request to save LocaleBoitier : {}", localeBoitier);
        return localeBoitierRepository.save(localeBoitier);
    }

    @Override
    public LocaleBoitier update(LocaleBoitier localeBoitier) {
        log.debug("Request to update LocaleBoitier : {}", localeBoitier);
        return localeBoitierRepository.save(localeBoitier);
    }

    @Override
    public Optional<LocaleBoitier> partialUpdate(LocaleBoitier localeBoitier) {
        log.debug("Request to partially update LocaleBoitier : {}", localeBoitier);

        return localeBoitierRepository
            .findById(localeBoitier.getId())
            .map(existingLocaleBoitier -> {
                if (localeBoitier.getDateDebut() != null) {
                    existingLocaleBoitier.setDateDebut(localeBoitier.getDateDebut());
                }
                if (localeBoitier.getDateFin() != null) {
                    existingLocaleBoitier.setDateFin(localeBoitier.getDateFin());
                }

                return existingLocaleBoitier;
            })
            .map(localeBoitierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocaleBoitier> findAll() {
        log.debug("Request to get all LocaleBoitiers");
        return localeBoitierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocaleBoitier> findOne(Long id) {
        log.debug("Request to get LocaleBoitier : {}", id);
        return localeBoitierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LocaleBoitier : {}", id);
        localeBoitierRepository.deleteById(id);
    }
}
