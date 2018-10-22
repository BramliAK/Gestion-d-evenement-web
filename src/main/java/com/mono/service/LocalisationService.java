package com.mono.service;

import com.mono.domain.Localisation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Localisation.
 */
public interface LocalisationService {

    /**
     * Save a localisation.
     *
     * @param localisation the entity to save
     * @return the persisted entity
     */
    Localisation save(Localisation localisation);

    /**
     * Get all the localisations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Localisation> findAll(Pageable pageable);


    /**
     * Get the "id" localisation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Localisation> findOne(Long id);

    /**
     * Delete the "id" localisation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
