package com.mono.service.impl;

import com.mono.service.DateeventService;
import com.mono.domain.Dateevent;
import com.mono.repository.DateeventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Dateevent.
 */
@Service
@Transactional
public class DateeventServiceImpl implements DateeventService {

    private final Logger log = LoggerFactory.getLogger(DateeventServiceImpl.class);

    private final DateeventRepository dateeventRepository;

    public DateeventServiceImpl(DateeventRepository dateeventRepository) {
        this.dateeventRepository = dateeventRepository;
    }

    /**
     * Save a dateevent.
     *
     * @param dateevent the entity to save
     * @return the persisted entity
     */
    @Override
    public Dateevent save(Dateevent dateevent) {
        log.debug("Request to save Dateevent : {}", dateevent);        return dateeventRepository.save(dateevent);
    }

    /**
     * Get all the dateevents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dateevent> findAll(Pageable pageable) {
        log.debug("Request to get all Dateevents");
        return dateeventRepository.findAll(pageable);
    }


    /**
     * Get one dateevent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dateevent> findOne(Long id) {
        log.debug("Request to get Dateevent : {}", id);
        return dateeventRepository.findById(id);
    }

    /**
     * Delete the dateevent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dateevent : {}", id);
        dateeventRepository.deleteById(id);
    }
}
