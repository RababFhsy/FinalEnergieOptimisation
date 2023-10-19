package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Etage;
import com.feb.optiisationenergie.repository.EtageRepository;
import com.feb.optiisationenergie.service.EtageService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Etage}.
 */
@Service
@Transactional
public class EtageServiceImpl implements EtageService {

    private final Logger log = LoggerFactory.getLogger(EtageServiceImpl.class);

    private final EtageRepository etageRepository;

    public EtageServiceImpl(EtageRepository etageRepository) {
        this.etageRepository = etageRepository;
    }

    @Override
    public Etage save(Etage etage) {
        log.debug("Request to save Etage : {}", etage);
        return etageRepository.save(etage);
    }

    @Override
    public Etage update(Etage etage) {
        log.debug("Request to update Etage : {}", etage);
        return etageRepository.save(etage);
    }

    @Override
    public Optional<Etage> partialUpdate(Etage etage) {
        log.debug("Request to partially update Etage : {}", etage);

        return etageRepository
            .findById(etage.getId())
            .map(existingEtage -> {
                if (etage.getEtageNumero() != null) {
                    existingEtage.setEtageNumero(etage.getEtageNumero());
                }

                return existingEtage;
            })
            .map(etageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Etage> findAll() {
        log.debug("Request to get all Etages");
        return etageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Etage> findOne(Long id) {
        log.debug("Request to get Etage : {}", id);
        return etageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etage : {}", id);
        etageRepository.deleteById(id);
    }
}
