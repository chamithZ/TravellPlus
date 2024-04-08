package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOffersDTO {
    private long rOfferId;
    private String offerName;
    private double discountPercentage;
    private long reservationId;
}
