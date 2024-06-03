package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepo extends JpaRepository<Hotel,Long> {

    List<Hotel> findByHotelCity(@Param("city") String city);

    @Query("SELECT h FROM Hotel h WHERE h.hotelStatus = true")

    Page<Hotel> findAllActiveHotels(Pageable pageable);

    Page<Hotel> findAll(Pageable pageable);
    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.contract c " +
            "JOIN c.roomType rt " +
            "JOIN rt.roomTypeSeasons rs " +
            "WHERE h.hotelCity = :city " +
            "AND ( :checkInDate >= c.startDate AND :checkOutDate <= c.endDate) " +
            "AND rt.roomSize >= :guestsPerRoom " +
            "AND EXISTS (SELECT 1 FROM rs.season s WHERE :checkInDate BETWEEN s.startDate AND s.endDate AND :checkOutDate BETWEEN s.startDate AND s.endDate)" +
            "AND h.hotelStatus = true " + // Add this condition
            "GROUP BY h " +
            "HAVING COALESCE(SUM(rs.noOfRooms), 0) >= :numberOfRooms")
    Page<Hotel> findAvailableHotels(@Param("city") String city,
                                    @Param("checkInDate") LocalDate checkInDate,
                                    @Param("checkOutDate") LocalDate checkOutDate,
                                    @Param("guestsPerRoom") int guestsPerRoom,
                                    @Param("numberOfRooms") int numberOfRooms,
                                    Pageable pageable);

}