package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Entity.ReservationSupplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSupplementRepo extends JpaRepository<ReservationSupplement,Integer> {

}
