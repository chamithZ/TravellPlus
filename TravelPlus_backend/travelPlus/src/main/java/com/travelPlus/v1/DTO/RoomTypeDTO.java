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
    private String roomSize;
    private String roomBedType;
    private String roomDescription;
    private String roomImage;
    private int contractId;
    private double price;
    private int noOfRooms;
    int seasonId;

}
