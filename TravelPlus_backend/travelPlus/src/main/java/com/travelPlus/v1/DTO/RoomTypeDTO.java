package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTypeDTO {
    private int roomId;
    private String roomTypeName;
    private double peakRoomPrice;
    private double offPeakRoomPrice;
    private int peakNoOfRooms;
    private int offPeakNoOfRooms;
    private String roomBedType;
    private String roomDescription;
    private int hotelId;

}
