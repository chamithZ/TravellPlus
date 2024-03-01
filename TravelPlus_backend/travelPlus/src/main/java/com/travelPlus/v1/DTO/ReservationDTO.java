package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int reservationId;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private String paymentMethod;
    private String reservedRoomType;

    private int roomTypeId;

}
