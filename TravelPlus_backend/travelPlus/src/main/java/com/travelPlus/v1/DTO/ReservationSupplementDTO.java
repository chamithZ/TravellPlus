package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSupplementDTO {
    private int rSupplementId;
    private double price;
    private String serviceName;
    private int reservationId;
}
