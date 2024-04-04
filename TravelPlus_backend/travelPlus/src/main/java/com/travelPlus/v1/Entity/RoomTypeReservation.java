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
public class RoomTypeReservation {
    @EmbeddedId
    private RoomTypeReservationKey  id=new RoomTypeReservationKey();
    @ManyToOne
    @MapsId("reservationId")
    @JoinColumn(name="reservation_id")
    @JsonIgnore
    private  Reservation reservation;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name="room_id")
    @JsonIgnore
    private RoomType roomType;
    private int roomsCount;


}
