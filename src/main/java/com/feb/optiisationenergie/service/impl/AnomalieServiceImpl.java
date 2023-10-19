package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Anomalie;
import com.feb.optiisationenergie.repository.AnomalieRepository;
import com.feb.optiisationenergie.service.AnomalieService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Anomalie}.
 */
@Service
@Transactional
public class AnomalieServiceImpl implements AnomalieService {

    private final Logger log = LoggerFactory.getLogger(AnomalieServiceImpl.class);

    private final AnomalieRepository anomalieRepository;

    public AnomalieServiceImpl(AnomalieRepository anomalieRepository) {
        this.anomalieRepository = anomalieRepository;
    }

    @Override
    public Anomalie save(Anomalie anomalie) {
        log.debug("Request to save Anomalie : {}", anomalie);
        return anomalieRepository.save(anomalie);
    }

    @Override
    public Anomalie update(Anomalie anomalie) {
        log.debug("Request to update Anomalie : {}", anomalie);
        return anomalieRepository.save(anomalie);
    }

    @Override
    public Optional<Anomalie> partialUpdate(Anomalie anomalie) {
        log.debug("Request to partially update Anomalie : {}", anomalie);

        return anomalieRepository
            .findById(anomalie.getId())
            .map(existingAnomalie -> {
                if (anomalie.getZoneNormaleMin() != null) {
                    existingAnomalie.setZoneNormaleMin(anomalie.getZoneNormaleMin());
                }
                if (anomalie.getZoneNormaleMax() != null) {
                    existingAnomalie.setZoneNormaleMax(anomalie.getZoneNormaleMax());
                }
                if (anomalie.getDateAnomalie() != null) {
                    existingAnomalie.setDateAnomalie(anomalie.getDateAnomalie());
                }
                if (anomalie.getDescriptionAnomalie() != null) {
                    existingAnomalie.setDescriptionAnomalie(anomalie.getDescriptionAnomalie());
                }

                return existingAnomalie;
            })
            .map(anomalieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Anomalie> findAll() {
        log.debug("Request to get all Anomalies");
        return anomalieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Anomalie> findOne(Long id) {
        log.debug("Request to get Anomalie : {}", id);
        return anomalieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anomalie : {}", id);
        anomalieRepository.deleteById(id);
    }
}
