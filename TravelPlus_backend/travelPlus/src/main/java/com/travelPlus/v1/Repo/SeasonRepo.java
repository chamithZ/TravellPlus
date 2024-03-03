package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepo extends JpaRepository<Season,Integer> {
    List<Season> findByContract_contractId(int contractId);
}

