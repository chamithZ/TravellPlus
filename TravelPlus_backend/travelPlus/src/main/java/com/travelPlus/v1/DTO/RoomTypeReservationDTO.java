package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeReservationDTO {
    private RoomType roomType;
    private Reservation reservation;
    private int roomCount;
    private double roomPrice;
}
