package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplementRepo extends JpaRepository<Supplement,Long> {
    List<Supplement> findByContract_contractId(long contractId);
}
