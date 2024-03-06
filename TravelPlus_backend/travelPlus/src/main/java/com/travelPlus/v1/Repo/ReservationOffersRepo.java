package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.ReservationOffers;
import com.travelPlus.v1.Entity.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationOffersRepo extends JpaRepository<ReservationOffers,Long> {
    List<ReservationOffers> findByReservation_reservationId(long reservationId);
}
