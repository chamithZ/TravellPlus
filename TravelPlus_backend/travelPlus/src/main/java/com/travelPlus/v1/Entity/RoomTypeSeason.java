package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomTypeSeason {
    @EmbeddedId
    RoomTypeSeasonKey id;

    @ManyToOne
    @MapsId("roomTypeId")
    RoomType roomType;

    @ManyToOne
    @MapsId("seasonId")
    Season season;

    double roomPrice;
    int noOfRooms;


}
