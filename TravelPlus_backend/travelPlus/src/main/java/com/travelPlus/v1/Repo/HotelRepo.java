package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepo extends JpaRepository<Hotel,Long> {

    List<Hotel> findByHotelCity(@Param("city") String city);

    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.contract c " +
            "JOIN c.roomType rt " +
            "JOIN rt.roomTypeSeasons rs " +
            "WHERE h.hotelCity = :city " +
            "AND ( :checkInDate >= c.startDate AND :checkOutDate <= c.endDate) " +
            "AND rt.roomSize >= :guestsPerRoom " +
            "AND NOT EXISTS (" +
            "   SELECT 1 FROM RoomTypeReservation rrt " +
            "   WHERE rrt.roomType = rt " +
            "   AND rrt.reservation.checkOutDate > :checkInDate " +
            "   AND rrt.reservation.checkInDate < :checkOutDate" +
            ")" +
            "AND EXISTS (SELECT 1 FROM rs.season s WHERE :checkInDate BETWEEN s.startDate AND s.endDate AND :checkOutDate BETWEEN s.startDate AND s.endDate)" +
            "GROUP BY h " +
            "HAVING COALESCE(SUM(rs.noOfRooms), 0) >= :numberOfRooms")
    List<Hotel> findAvailableHotels(@Param("city") String city,
                                    @Param("checkInDate") LocalDate checkInDate,
                                    @Param("checkOutDate") LocalDate checkOutDate,
                                    @Param("guestsPerRoom") int guestsPerRoom,
                                    @Param("numberOfRooms") int numberOfRooms);



}