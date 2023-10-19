package com.feb.optiisationenergie.service.impl;

import com.feb.optiisationenergie.domain.Capteur;
import com.feb.optiisationenergie.repository.CapteurRepository;
import com.feb.optiisationenergie.service.CapteurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Capteur}.
 */
@Service
@Transactional
public class CapteurServiceImpl implements CapteurService {

    private final Logger log = LoggerFactory.getLogger(CapteurServiceImpl.class);

    private final CapteurRepository capteurRepository;

    public CapteurServiceImpl(CapteurRepository capteurRepository) {
        this.capteurRepository = capteurRepository;
    }

    @Override
    public Capteur save(Capteur capteur) {
        log.debug("Request to save Capteur : {}", capteur);
        return capteurRepository.save(capteur);
    }

    @Override
    public Capteur update(Capteur capteur) {
        log.debug("Request to update Capteur : {}", capteur);
        return capteurRepository.save(capteur);
    }

    @Override
    public Optional<Capteur> partialUpdate(Capteur capteur) {
        log.debug("Request to partially update Capteur : {}", capteur);

        return capteurRepository
            .findById(capteur.getId())
            .map(existingCapteur -> {
                if (capteur.getCapteurReference() != null) {
                    existingCapteur.setCapteurReference(capteur.getCapteurReference());
                }
                if (capteur.getType() != null) {
                    existingCapteur.setType(capteur.getType());
                }
                if (capteur.getPhoto() != null) {
                    existingCapteur.setPhoto(capteur.getPhoto());
                }
                if (capteur.getPhotoContentType() != null) {
                    existingCapteur.setPhotoContentType(capteur.getPhotoContentType());
                }
                if (capteur.getValeurMin() != null) {
                    existingCapteur.setValeurMin(capteur.getValeurMin());
                }
                if (capteur.getValeurMax() != null) {
                    existingCapteur.setValeurMax(capteur.getValeurMax());
                }

                return existingCapteur;
            })
            .map(capteurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Capteur> findAll() {
        log.debug("Request to get all Capteurs");
        return capteurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Capteur> findOne(Long id) {
        log.debug("Request to get Capteur : {}", id);
        return capteurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Capteur : {}", id);
        capteurRepository.deleteById(id);
    }
}
