package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel,Integer> {

}
