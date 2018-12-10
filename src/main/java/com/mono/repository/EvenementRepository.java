package com.mono.repository;

import com.mono.domain.Evenement;
import com.mono.domain.enumeration.Typeevent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Evenement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    @Query(value = "select distinct evenement from Evenement evenement left join fetch evenement.dateevents left join fetch evenement.localisations",
        countQuery = "select count(distinct evenement) from Evenement evenement")
    Page<Evenement> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct evenement from Evenement evenement left join fetch evenement.dateevents left join fetch evenement.localisations")
    List<Evenement> findAllWithEagerRelationships();

    @Query("select evenement from Evenement evenement left join fetch evenement.dateevents left join fetch evenement.localisations where evenement.id =:id")
    Optional<Evenement> findOneWithEagerRelationships(@Param("id") Long id);

    List<Evenement> findEvenementsByTypeevnet(Typeevent type);

}
