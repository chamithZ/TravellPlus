package com.travelPlus.v1.Repo;


import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepo extends JpaRepository <Offer,Long>{
    List<Offer> findByContract_contractId(int contractId);
}
