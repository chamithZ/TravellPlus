package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;
    private double rmarkUpPercentage;
    private double rprepaymentPercentage;
    private int rcancellationDeadline;
    private int rPaymentDeadline;
    private double rcancellationPercentage;
    private double totalPrice;

    @OneToOne  // payment - reservation relation
    @JoinColumn(name="reservationId" , referencedColumnName = "reservationId")
    private Reservation reservation;

}
