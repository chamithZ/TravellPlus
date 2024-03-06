package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
    List<RoomType> findByContract_contractId(long contractId);
}
