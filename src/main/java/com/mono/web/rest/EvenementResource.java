package com.mono.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mono.domain.Evenement;
import com.mono.domain.Localisation;
import com.mono.domain.enumeration.Typeevent;
import com.mono.service.EvenementService;
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
import java.util.Set;

/**
 * REST controller for managing Evenement.
 */
@RestController
@RequestMapping("/api")
public class EvenementResource {

    private final Logger log = LoggerFactory.getLogger(EvenementResource.class);

    private static final String ENTITY_NAME = "evenement";

    private final EvenementService evenementService;


    public EvenementResource(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    /**
     * POST  /evenements : Create a new evenement.
     *
     * @param evenement the evenement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evenement, or with status 400 (Bad Request) if the evenement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evenements")
    @Timed
    public ResponseEntity<Evenement> createEvenement(@Valid @RequestBody Evenement evenement) throws URISyntaxException {
        log.debug("REST request to save Evenement : {}", evenement);
        if (evenement.getId() != null) {
            throw new BadRequestAlertException("A new evenement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Evenement result = evenementService.save(evenement);
        return ResponseEntity.created(new URI("/api/evenements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evenements : Updates an existing evenement.
     *
     * @param evenement the evenement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evenement,
     * or with status 400 (Bad Request) if the evenement is not valid,
     * or with status 500 (Internal Server Error) if the evenement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evenements")
    @Timed
    public ResponseEntity<Evenement> updateEvenement(@Valid @RequestBody Evenement evenement) throws URISyntaxException {
        log.debug("REST request to update Evenement : {}", evenement);
        if (evenement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Evenement result = evenementService.save(evenement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evenement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evenements : get all the evenements.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of evenements in body
     */
    @GetMapping("/evenements")
    @Timed
    public ResponseEntity<List<Evenement>> getAllEvenements(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Evenements");
        Page<Evenement> page;
        if (eagerload) {
            page = evenementService.findAllWithEagerRelationships(pageable);
        } else {
            page = evenementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/evenements?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evenements/:id : get the "id" evenement.
     *
     * @param id the id of the evenement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evenement, or with status 404 (Not Found)
     */
    @GetMapping("/evenements/{id}")
    @Timed
    public ResponseEntity<Evenement> getEvenement(@PathVariable Long id) {
        log.debug("REST request to get Evenement : {}", id);
        Optional<Evenement> evenement = evenementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evenement);
    }


    @GetMapping("/evenementstypeid/{id}")
    @Timed
    public Set<Localisation> getlocalisationformevent (@PathVariable Long id) {
        Optional<Evenement> e=evenementService.findOne(id);

        return  e.get().getLocalisations();
    }

    /**
     * GET  /evenements/:id : get the "id" evenement.
     *
     * @param type the id of the evenement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evenement, or with status 404 (Not Found)
     */
    @GetMapping("/evenementstype/{type}")
    @Timed
    public List<Evenement> getEvenementbytype(@PathVariable Typeevent type ) {
        //log.debug("REST request to get Evenement : {}", type);
        //Optional<Evenement> evenement = evenementResource.
        return evenementService.findEvenementsByTypeevnet(type);
    }

    /**
     * DELETE  /evenements/:id : delete the "id" evenement.
     *
     * @param id the id of the evenement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evenements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        log.debug("REST request to delete Evenement : {}", id);
        evenementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
