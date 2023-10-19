package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Batiment;
import com.feb.optiisationenergie.repository.BatimentRepository;
import com.feb.optiisationenergie.service.BatimentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Batiment}.
 */
@Service
@Transactional
public class BatimentServiceImpl implements BatimentService {

    private final Logger log = LoggerFactory.getLogger(BatimentServiceImpl.class);

    private final BatimentRepository batimentRepository;

    public BatimentServiceImpl(BatimentRepository batimentRepository) {
        this.batimentRepository = batimentRepository;
    }

    @Override
    public Batiment save(Batiment batiment) {
        log.debug("Request to save Batiment : {}", batiment);
        return batimentRepository.save(batiment);
    }

    @Override
    public Batiment update(Batiment batiment) {
        log.debug("Request to update Batiment : {}", batiment);
        return batimentRepository.save(batiment);
    }

    @Override
    public Optional<Batiment> partialUpdate(Batiment batiment) {
        log.debug("Request to partially update Batiment : {}", batiment);

        return batimentRepository
            .findById(batiment.getId())
            .map(existingBatiment -> {
                if (batiment.getAdresse() != null) {
                    existingBatiment.setAdresse(batiment.getAdresse());
                }
                if (batiment.getBatimentNom() != null) {
                    existingBatiment.setBatimentNom(batiment.getBatimentNom());
                }

                return existingBatiment;
            })
            .map(batimentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Batiment> findAll() {
        log.debug("Request to get all Batiments");
        return batimentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Batiment> findOne(Long id) {
        log.debug("Request to get Batiment : {}", id);
        return batimentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Batiment : {}", id);
        batimentRepository.deleteById(id);
    }
}
