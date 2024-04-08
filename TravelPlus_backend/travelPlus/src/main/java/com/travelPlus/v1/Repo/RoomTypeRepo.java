package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
    List<RoomType> findByContract_contractId(long contractId);

    // Modified query to fetch room types along with room type seasons
    @Query("SELECT DISTINCT rt FROM RoomType rt " +
            "JOIN FETCH rt.roomTypeSeasons rts " + // Fetch roomTypeSeasons collection
            "JOIN FETCH rts.season s " +
            "WHERE rt.contract.contractId = :contractId " +
            "AND s.startDate <= :checkOutDate " +
            "AND s.endDate >= :checkInDate")
    List<RoomType> findRoomTypesByContractIdAndSeasonValidity(
            @Param("contractId") long contractId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
    @Query("SELECT rt.roomId, (rs.noOfRooms - COALESCE(SUM(CASE WHEN rtr IS NULL THEN 0 ELSE rtr.roomsCount END), 0)) AS availableRoomsCount " +
            "FROM RoomType rt " +
            "JOIN rt.roomTypeSeasons rs " +
            "LEFT JOIN rt.roomTypeReservation rtr " +
            "ON rtr.roomType.roomId = rt.roomId " +
            "AND (rtr.reservation.checkInDate BETWEEN :checkInDate AND :checkOutDate OR rtr.reservation.checkOutDate BETWEEN :checkInDate AND :checkOutDate) " +
            "AND EXISTS (SELECT 1 FROM rs.season s WHERE :checkInDate BETWEEN s.startDate AND s.endDate AND :checkOutDate BETWEEN s.startDate AND s.endDate) " +
            "WHERE rt.contract.contractId= :contractId " + // Filter by contractId
            "GROUP BY rt.roomId")
    List<Object[]> findAvailableRoomTypes(@Param("contractId") long contractId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

}
