package com.mono.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.domain.Dateevent;
import com.mono.service.DateeventService;
import com.mono.web.rest.errors.BadRequestAlertException;
import com.mono.web.rest.util.HeaderUtil;
import com.mono.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dateevent.
 */
@RestController
@RequestMapping("/api")
public class DateeventResource {

    private final Logger log = LoggerFactory.getLogger(DateeventResource.class);

    private static final String ENTITY_NAME = "dateevent";

    private final DateeventService dateeventService;

    public DateeventResource(DateeventService dateeventService) {
        this.dateeventService = dateeventService;
    }

    /**
     * POST  /dateevents : Create a new dateevent.
     *
     * @param dateevent the dateevent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dateevent, or with status 400 (Bad Request) if the dateevent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dateevents")
    @Timed
    public ResponseEntity<Dateevent> createDateevent(@Valid @RequestBody Dateevent dateevent) throws URISyntaxException {
        log.debug("REST request to save Dateevent : {}", dateevent);
        if (dateevent.getId() != null) {
            throw new BadRequestAlertException("A new dateevent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dateevent result = dateeventService.save(dateevent);
        return ResponseEntity.created(new URI("/api/dateevents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dateevents : Updates an existing dateevent.
     *
     * @param dateevent the dateevent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dateevent,
     * or with status 400 (Bad Request) if the dateevent is not valid,
     * or with status 500 (Internal Server Error) if the dateevent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dateevents")
    @Timed
    public ResponseEntity<Dateevent> updateDateevent(@Valid @RequestBody Dateevent dateevent) throws URISyntaxException {
        log.debug("REST request to update Dateevent : {}", dateevent);
        if (dateevent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dateevent result = dateeventService.save(dateevent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dateevent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dateevents : get all the dateevents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dateevents in body
     */
    @GetMapping("/dateevents")
    @Timed
    public ResponseEntity<List<Dateevent>> getAllDateevents(Pageable pageable) {
        log.debug("REST request to get a page of Dateevents");
        Page<Dateevent> page = dateeventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dateevents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dateevents/:id : get the "id" dateevent.
     *
     * @param id the id of the dateevent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dateevent, or with status 404 (Not Found)
     */
    @GetMapping("/dateevents/{id}")
    @Timed
    public ResponseEntity<Dateevent> getDateevent(@PathVariable Long id) {
        log.debug("REST request to get Dateevent : {}", id);
        Optional<Dateevent> dateevent = dateeventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dateevent);
    }

    /**
     * DELETE  /dateevents/:id : delete the "id" dateevent.
     *
     * @param id the id of the dateevent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dateevents/{id}")
    @Timed
    public ResponseEntity<Void> deleteDateevent(@PathVariable Long id) {
        log.debug("REST request to delete Dateevent : {}", id);
        dateeventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
