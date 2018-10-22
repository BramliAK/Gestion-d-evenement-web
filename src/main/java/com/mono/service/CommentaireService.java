package com.mono.service;

import com.mono.domain.Commentaire;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Commentaire.
 */
public interface CommentaireService {

    /**
     * Save a commentaire.
     *
     * @param commentaire the entity to save
     * @return the persisted entity
     */
    Commentaire save(Commentaire commentaire);

    /**
     * Get all the commentaires.
     *
     * @return the list of entities
     */
    List<Commentaire> findAll();


    /**
     * Get the "id" commentaire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Commentaire> findOne(Long id);

    /**
     * Delete the "id" commentaire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
