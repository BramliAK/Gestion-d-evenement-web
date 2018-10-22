package com.mono.service.impl;

import com.mono.service.CommentaireService;
import com.mono.domain.Commentaire;
import com.mono.repository.CommentaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Commentaire.
 */
@Service
@Transactional
public class CommentaireServiceImpl implements CommentaireService {

    private final Logger log = LoggerFactory.getLogger(CommentaireServiceImpl.class);

    private final CommentaireRepository commentaireRepository;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    /**
     * Save a commentaire.
     *
     * @param commentaire the entity to save
     * @return the persisted entity
     */
    @Override
    public Commentaire save(Commentaire commentaire) {
        log.debug("Request to save Commentaire : {}", commentaire);        return commentaireRepository.save(commentaire);
    }

    /**
     * Get all the commentaires.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Commentaire> findAll() {
        log.debug("Request to get all Commentaires");
        return commentaireRepository.findAll();
    }


    /**
     * Get one commentaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Commentaire> findOne(Long id) {
        log.debug("Request to get Commentaire : {}", id);
        return commentaireRepository.findById(id);
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commentaire : {}", id);
        commentaireRepository.deleteById(id);
    }
}
