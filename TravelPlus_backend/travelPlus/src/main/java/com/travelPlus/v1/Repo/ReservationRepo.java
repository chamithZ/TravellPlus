package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
}
