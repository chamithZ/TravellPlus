package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
    Optional<Payment>  findByReservation_ReservationId(long reservationId);
}
