package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepo extends JpaRepository<Hotel,Long> {

    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.contract c " +
            "WHERE h.hotelCity = :destination " +
            "AND c.startDate <= :checkOutDate " +
            "AND c.endDate >= :checkInDate")
    List<Hotel> findHotelsByCityAndContractDates(@Param("destination") String destination,
                                                 @Param("checkInDate") String checkInDate,
                                                 @Param("checkOutDate") String checkOutDate);
}
