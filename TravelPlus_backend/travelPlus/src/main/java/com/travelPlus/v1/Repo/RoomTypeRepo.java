package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepo extends JpaRepository<RoomType,Integer> {
    List<RoomType> findByHotel_HotelId(int hotelId);
}
