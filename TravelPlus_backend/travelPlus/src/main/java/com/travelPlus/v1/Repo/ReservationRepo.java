package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Long> {
    List<Reservation> findByUser_userId(long userId);
    List<Reservation> findByHotel_HotelId(long hotelId);
}
