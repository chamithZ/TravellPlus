package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
