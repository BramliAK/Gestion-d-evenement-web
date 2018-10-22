package com.mono.service.impl;

import com.mono.service.LocalisationService;
import com.mono.domain.Localisation;
import com.mono.repository.LocalisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Localisation.
 */
@Service
@Transactional
public class LocalisationServiceImpl implements LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationServiceImpl.class);

    private final LocalisationRepository localisationRepository;

    public LocalisationServiceImpl(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    /**
     * Save a localisation.
     *
     * @param localisation the entity to save
     * @return the persisted entity
     */
    @Override
    public Localisation save(Localisation localisation) {
        log.debug("Request to save Localisation : {}", localisation);        return localisationRepository.save(localisation);
    }

    /**
     * Get all the localisations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Localisation> findAll(Pageable pageable) {
        log.debug("Request to get all Localisations");
        return localisationRepository.findAll(pageable);
    }


    /**
     * Get one localisation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Localisation> findOne(Long id) {
        log.debug("Request to get Localisation : {}", id);
        return localisationRepository.findById(id);
    }

    /**
     * Delete the localisation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localisation : {}", id);
        localisationRepository.deleteById(id);
    }
}
