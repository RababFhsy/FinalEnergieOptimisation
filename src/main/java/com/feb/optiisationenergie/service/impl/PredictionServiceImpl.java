package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Prediction;
import com.feb.optiisationenergie.repository.PredictionRepository;
import com.feb.optiisationenergie.service.PredictionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Prediction}.
 */
@Service
@Transactional
public class PredictionServiceImpl implements PredictionService {

    private final Logger log = LoggerFactory.getLogger(PredictionServiceImpl.class);

    private final PredictionRepository predictionRepository;

    public PredictionServiceImpl(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    @Override
    public Prediction save(Prediction prediction) {
        log.debug("Request to save Prediction : {}", prediction);
        return predictionRepository.save(prediction);
    }

    @Override
    public Prediction update(Prediction prediction) {
        log.debug("Request to update Prediction : {}", prediction);
        return predictionRepository.save(prediction);
    }

    @Override
    public Optional<Prediction> partialUpdate(Prediction prediction) {
        log.debug("Request to partially update Prediction : {}", prediction);

        return predictionRepository
            .findById(prediction.getId())
            .map(existingPrediction -> {
                if (prediction.getDateDebut() != null) {
                    existingPrediction.setDateDebut(prediction.getDateDebut());
                }
                if (prediction.getDateFin() != null) {
                    existingPrediction.setDateFin(prediction.getDateFin());
                }
                if (prediction.getConsommationPredit() != null) {
                    existingPrediction.setConsommationPredit(prediction.getConsommationPredit());
                }
                if (prediction.getPrecision() != null) {
                    existingPrediction.setPrecision(prediction.getPrecision());
                }

                return existingPrediction;
            })
            .map(predictionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prediction> findAll() {
        log.debug("Request to get all Predictions");
        return predictionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prediction> findOne(Long id) {
        log.debug("Request to get Prediction : {}", id);
        return predictionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prediction : {}", id);
        predictionRepository.deleteById(id);
    }
}
