package com.mono.service;

import com.mono.domain.Dateevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dateevent.
 */
public interface DateeventService {

    /**
     * Save a dateevent.
     *
     * @param dateevent the entity to save
     * @return the persisted entity
     */
    Dateevent save(Dateevent dateevent);

    /**
     * Get all the dateevents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dateevent> findAll(Pageable pageable);


    /**
     * Get the "id" dateevent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Dateevent> findOne(Long id);

    /**
     * Delete the "id" dateevent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
