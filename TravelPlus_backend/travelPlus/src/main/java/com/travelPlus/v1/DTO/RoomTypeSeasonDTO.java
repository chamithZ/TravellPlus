package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeSeasonDTO {
    RoomType roomType;
    Season season;
    double roomPrice;
    int noOfRooms;
}
