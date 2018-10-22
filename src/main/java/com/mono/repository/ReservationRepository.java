package com.mono.repository;

import com.mono.domain.Reservation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Reservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select reservation from Reservation reservation where reservation.user.login = ?#{principal.username}")
    List<Reservation> findByUserIsCurrentUser();

}
