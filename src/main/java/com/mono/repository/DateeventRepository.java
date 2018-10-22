package com.mono.repository;

import com.mono.domain.Dateevent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Dateevent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DateeventRepository extends JpaRepository<Dateevent, Long> {

}
