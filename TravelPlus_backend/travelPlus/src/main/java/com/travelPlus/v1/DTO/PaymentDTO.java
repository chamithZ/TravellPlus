package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private long paymentId;
    private double rmarkUpPercentage;
    private int rcancellationDeadline;
    private int rPaymentDeadline;
    private double rcancellationPercentage;
    private double totalPrice;
    private long reservationId;
}
