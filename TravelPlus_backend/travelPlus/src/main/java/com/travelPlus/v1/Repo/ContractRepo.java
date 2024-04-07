package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepo extends JpaRepository<Contract,Long> {
   Contract findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(
            long hotelId, LocalDate endDate, LocalDate startDate
    );
}
