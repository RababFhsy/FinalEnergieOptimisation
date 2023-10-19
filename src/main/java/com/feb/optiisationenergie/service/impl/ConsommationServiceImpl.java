package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Consommation;
import com.feb.optiisationenergie.repository.ConsommationRepository;
import com.feb.optiisationenergie.service.ConsommationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Consommation}.
 */
@Service
@Transactional
public class ConsommationServiceImpl implements ConsommationService {

    private final Logger log = LoggerFactory.getLogger(ConsommationServiceImpl.class);

    private final ConsommationRepository consommationRepository;

    public ConsommationServiceImpl(ConsommationRepository consommationRepository) {
        this.consommationRepository = consommationRepository;
    }

    @Override
    public Consommation save(Consommation consommation) {
        log.debug("Request to save Consommation : {}", consommation);
        return consommationRepository.save(consommation);
    }

    @Override
    public Consommation update(Consommation consommation) {
        log.debug("Request to update Consommation : {}", consommation);
        return consommationRepository.save(consommation);
    }

    @Override
    public Optional<Consommation> partialUpdate(Consommation consommation) {
        log.debug("Request to partially update Consommation : {}", consommation);

        return consommationRepository
            .findById(consommation.getId())
            .map(existingConsommation -> {
                if (consommation.getEnergieConsommation() != null) {
                    existingConsommation.setEnergieConsommation(consommation.getEnergieConsommation());
                }
                if (consommation.getDateConsommation() != null) {
                    existingConsommation.setDateConsommation(consommation.getDateConsommation());
                }

                return existingConsommation;
            })
            .map(consommationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consommation> findAll() {
        log.debug("Request to get all Consommations");
        return consommationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Consommation> findOne(Long id) {
        log.debug("Request to get Consommation : {}", id);
        return consommationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consommation : {}", id);
        consommationRepository.deleteById(id);
    }
}
