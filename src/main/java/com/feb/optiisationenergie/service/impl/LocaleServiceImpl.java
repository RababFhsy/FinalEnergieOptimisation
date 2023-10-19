package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Locale;
import com.feb.optiisationenergie.repository.LocaleRepository;
import com.feb.optiisationenergie.service.LocaleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Locale}.
 */
@Service
@Transactional
public class LocaleServiceImpl implements LocaleService {

    private final Logger log = LoggerFactory.getLogger(LocaleServiceImpl.class);

    private final LocaleRepository localeRepository;

    public LocaleServiceImpl(LocaleRepository localeRepository) {
        this.localeRepository = localeRepository;
    }

    @Override
    public Locale save(Locale locale) {
        log.debug("Request to save Locale : {}", locale);
        return localeRepository.save(locale);
    }

    @Override
    public Locale update(Locale locale) {
        log.debug("Request to update Locale : {}", locale);
        return localeRepository.save(locale);
    }

    @Override
    public Optional<Locale> partialUpdate(Locale locale) {
        log.debug("Request to partially update Locale : {}", locale);

        return localeRepository
            .findById(locale.getId())
            .map(existingLocale -> {
                if (locale.getNumero() != null) {
                    existingLocale.setNumero(locale.getNumero());
                }
                if (locale.getTypeLocal() != null) {
                    existingLocale.setTypeLocal(locale.getTypeLocal());
                }

                return existingLocale;
            })
            .map(localeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Locale> findAll() {
        log.debug("Request to get all Locales");
        return localeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Locale> findOne(Long id) {
        log.debug("Request to get Locale : {}", id);
        return localeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Locale : {}", id);
        localeRepository.deleteById(id);
    }
}
