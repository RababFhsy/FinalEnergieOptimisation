package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Energie;
import com.feb.optiisationenergie.repository.EnergieRepository;
import com.feb.optiisationenergie.service.EnergieService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Energie}.
 */
@Service
@Transactional
public class EnergieServiceImpl implements EnergieService {

    private final Logger log = LoggerFactory.getLogger(EnergieServiceImpl.class);

    private final EnergieRepository energieRepository;

    public EnergieServiceImpl(EnergieRepository energieRepository) {
        this.energieRepository = energieRepository;
    }

    @Override
    public Energie save(Energie energie) {
        log.debug("Request to save Energie : {}", energie);
        return energieRepository.save(energie);
    }

    @Override
    public Energie update(Energie energie) {
        log.debug("Request to update Energie : {}", energie);
        return energieRepository.save(energie);
    }

    @Override
    public Optional<Energie> partialUpdate(Energie energie) {
        log.debug("Request to partially update Energie : {}", energie);

        return energieRepository
            .findById(energie.getId())
            .map(existingEnergie -> {
                if (energie.getNomSystemEnergitique() != null) {
                    existingEnergie.setNomSystemEnergitique(energie.getNomSystemEnergitique());
                }

                return existingEnergie;
            })
            .map(energieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Energie> findAll() {
        log.debug("Request to get all Energies");
        return energieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Energie> findOne(Long id) {
        log.debug("Request to get Energie : {}", id);
        return energieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Energie : {}", id);
        energieRepository.deleteById(id);
    }
}
