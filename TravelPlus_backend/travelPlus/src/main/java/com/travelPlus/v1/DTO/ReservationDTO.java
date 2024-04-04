package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.ReservationRoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private long reservationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int childCount;
    private int adultCount;
    private long userId;
    private ReservationRoomType reservationRoomType;
    private PaymentDTO paymentDTO;
    private  boolean isFullPayment;
    private Collection<RoomTypeReservationDTO> roomTypeReservation=new ArrayList<>();
    private Collection<ReservationSupplementDTO> reservationSupplementDTOS=new ArrayList<>();
    private Collection<ReservationOffersDTO> reservationOffersDTOS=new ArrayList<>();

}
