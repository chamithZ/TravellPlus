package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Entity.ReservationOffers;
import com.travelPlus.v1.Entity.ReservationSupplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationSupplementRepo extends JpaRepository<ReservationSupplement,Long> {
    List<ReservationSupplement> findByReservation_reservationId(long reservationId);
}
