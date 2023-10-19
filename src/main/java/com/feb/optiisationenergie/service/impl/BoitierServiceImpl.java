package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Boitier;
import com.feb.optiisationenergie.repository.BoitierRepository;
import com.feb.optiisationenergie.service.BoitierService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Boitier}.
 */
@Service
@Transactional
public class BoitierServiceImpl implements BoitierService {

    private final Logger log = LoggerFactory.getLogger(BoitierServiceImpl.class);

    private final BoitierRepository boitierRepository;

    public BoitierServiceImpl(BoitierRepository boitierRepository) {
        this.boitierRepository = boitierRepository;
    }

    @Override
    public Boitier save(Boitier boitier) {
        log.debug("Request to save Boitier : {}", boitier);
        return boitierRepository.save(boitier);
    }

    @Override
    public Boitier update(Boitier boitier) {
        log.debug("Request to update Boitier : {}", boitier);
        return boitierRepository.save(boitier);
    }

    @Override
    public Optional<Boitier> partialUpdate(Boitier boitier) {
        log.debug("Request to partially update Boitier : {}", boitier);

        return boitierRepository
            .findById(boitier.getId())
            .map(existingBoitier -> {
                if (boitier.getBoitierReference() != null) {
                    existingBoitier.setBoitierReference(boitier.getBoitierReference());
                }
                if (boitier.getType() != null) {
                    existingBoitier.setType(boitier.getType());
                }
                if (boitier.getNbrBranche() != null) {
                    existingBoitier.setNbrBranche(boitier.getNbrBranche());
                }

                return existingBoitier;
            })
            .map(boitierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Boitier> findAll() {
        log.debug("Request to get all Boitiers");
        return boitierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boitier> findOne(Long id) {
        log.debug("Request to get Boitier : {}", id);
        return boitierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Boitier : {}", id);
        boitierRepository.deleteById(id);
    }
}
