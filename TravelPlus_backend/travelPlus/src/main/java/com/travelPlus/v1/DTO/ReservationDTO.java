package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int reservationId;
    private String checkInDate;
    private String checkOutDate;
    private int childCount;
    private int adultCount;
    boolean isFullPayment;


    private int roomTypeId;

}
