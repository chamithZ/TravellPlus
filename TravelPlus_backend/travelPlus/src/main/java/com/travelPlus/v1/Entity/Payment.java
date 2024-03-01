package com.travelPlus.v1.Entity;

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
    private int paymentId;
    private double markUpPercentage;
    private double rMarkupPercentage;
    private int rPaymentDeadline;
    private double cancellationPercentage;
    private double totalPrice;


}
