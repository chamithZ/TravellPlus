package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.ReservationRoomType;
import com.travelPlus.v1.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRoomTypeRepo extends JpaRepository<ReservationRoomType,Long> {
    List<ReservationRoomType> findByReservation_reservationId(long reservationId);
}
