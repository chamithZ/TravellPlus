package com.travelPlus.v1.Service;

import com.travelPlus.v1.Repo.ReservationRoomTypeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReservationRoomTypeService {

    @Autowired
    ReservationRoomTypeRepo reservationRoomTypeRepo;
    public List<com.travelPlus.v1.Entity.ReservationRoomType> getAllReservedRooms(long reservationId){
        List<com.travelPlus.v1.Entity.ReservationRoomType> roomList=reservationRoomTypeRepo.findByReservation_reservationId(reservationId);
        return roomList;
    }
}
