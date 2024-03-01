package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private int paymentId;
    private double markUpPercentage;
    private double rMarkupPercentage;
    private int rPaymentDeadline;
    private double cancellationPercentage;
    private double totalPrice;
}
