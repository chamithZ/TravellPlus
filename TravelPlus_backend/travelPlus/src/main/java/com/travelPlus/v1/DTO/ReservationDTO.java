package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.ReservationRoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private long reservationId;
    private String checkInDate;
    private String checkOutDate;
    private int childCount;
    private int adultCount;
    boolean isFullPayment;
    private long roomTypeId;
    private long userId;
    private ReservationRoomType reservationRoomType;
    private PaymentDTO paymentDTO;

}
