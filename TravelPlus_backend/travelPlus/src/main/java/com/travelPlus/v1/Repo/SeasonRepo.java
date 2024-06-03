package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeasonRepo extends JpaRepository<Season,Long> {
    List<Season> findByContract_contractId(long contractId);
    @Query("SELECT s FROM Season s WHERE s.contract.contractId=:contractId AND s.startDate <= :checkOut AND s.endDate >= :checkIn")
    Optional<Season> findSeasonByDates(@Param("contractId") long contractId, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

}

