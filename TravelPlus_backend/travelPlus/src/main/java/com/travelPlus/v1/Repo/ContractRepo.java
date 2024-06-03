package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ContractRepo extends JpaRepository<Contract,Long> {
 /*
    Contract findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(
            long hotelId, LocalDate endDate, LocalDate startDate
    );
*/
 @Query("SELECT c FROM Contract c " +
         "WHERE c.hotel.hotelId = :hotelId " +
         "AND c.startDate <= :endDate " +
         "AND c.endDate >= :startDate")
 Contract findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(
         long hotelId, LocalDate endDate, LocalDate startDate
 );


 @Query("SELECT c FROM Contract c WHERE c.contractStatus != false")
    Page<Contract> findActiveContracts(Pageable pageable);
    @Query("SELECT c FROM Contract c WHERE c.contractStatus != true")
    Page<Contract> findDeactiveContracts(Pageable pageable);

}
