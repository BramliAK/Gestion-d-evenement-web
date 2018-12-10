package com.mono.service;

import com.mono.domain.Evenement;

import com.mono.domain.enumeration.Typeevent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Evenement.
 */
public interface EvenementService {

    /**
     * Save a evenement.
     *
     * @param evenement the entity to save
     * @return the persisted entity
     */
    Evenement save(Evenement evenement);

    /**
     * Get all the evenements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Evenement> findAll(Pageable pageable);

    /**
     * Get all the Evenement with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Evenement> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" evenement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Evenement> findOne(Long id);

    /**
     * Get the "type" evenement.
     *
     * @param type the id of the entity
     * @return the list of entities
     */
      List<Evenement> findEvenementsByTypeevnet(Typeevent type);

    /**
     * Delete the "id" evenement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
