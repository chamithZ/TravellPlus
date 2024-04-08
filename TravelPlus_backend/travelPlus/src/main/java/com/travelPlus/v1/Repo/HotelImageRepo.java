package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImageRepo extends JpaRepository<HotelImage,Long> {
}
