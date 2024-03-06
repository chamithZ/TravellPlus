package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomTypeSeason {
    @EmbeddedId //composite Id
    RoomTypeSeasonKey id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name="room_id")
    @JsonIgnore
    RoomType roomType;

    @ManyToOne
    @MapsId("seasonId")
    @JoinColumn(name="season_id")
    Season season;

    double roomPrice;
    int noOfRooms;


}
