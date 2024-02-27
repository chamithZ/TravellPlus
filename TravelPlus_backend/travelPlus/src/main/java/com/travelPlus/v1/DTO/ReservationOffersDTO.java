package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationOffersDTO {
    private int rOfferId;
    private String offerName;
    private String discounPrecentage;
    private int reservationId;
}
