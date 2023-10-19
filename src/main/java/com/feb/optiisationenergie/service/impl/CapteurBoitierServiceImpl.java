package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.CapteurBoitier;
import com.feb.optiisationenergie.repository.CapteurBoitierRepository;
import com.feb.optiisationenergie.service.CapteurBoitierService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CapteurBoitier}.
 */
@Service
@Transactional
public class CapteurBoitierServiceImpl implements CapteurBoitierService {

    private final Logger log = LoggerFactory.getLogger(CapteurBoitierServiceImpl.class);

    private final CapteurBoitierRepository capteurBoitierRepository;

    public CapteurBoitierServiceImpl(CapteurBoitierRepository capteurBoitierRepository) {
        this.capteurBoitierRepository = capteurBoitierRepository;
    }

    @Override
    public CapteurBoitier save(CapteurBoitier capteurBoitier) {
        log.debug("Request to save CapteurBoitier : {}", capteurBoitier);
        return capteurBoitierRepository.save(capteurBoitier);
    }

    @Override
    public CapteurBoitier update(CapteurBoitier capteurBoitier) {
        log.debug("Request to update CapteurBoitier : {}", capteurBoitier);
        return capteurBoitierRepository.save(capteurBoitier);
    }

    @Override
    public Optional<CapteurBoitier> partialUpdate(CapteurBoitier capteurBoitier) {
        log.debug("Request to partially update CapteurBoitier : {}", capteurBoitier);

        return capteurBoitierRepository
            .findById(capteurBoitier.getId())
            .map(existingCapteurBoitier -> {
                if (capteurBoitier.getBranche() != null) {
                    existingCapteurBoitier.setBranche(capteurBoitier.getBranche());
                }

                return existingCapteurBoitier;
            })
            .map(capteurBoitierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CapteurBoitier> findAll() {
        log.debug("Request to get all CapteurBoitiers");
        return capteurBoitierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CapteurBoitier> findOne(Long id) {
        log.debug("Request to get CapteurBoitier : {}", id);
        return capteurBoitierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CapteurBoitier : {}", id);
        capteurBoitierRepository.deleteById(id);
    }
}
