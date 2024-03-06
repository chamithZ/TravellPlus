package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rRoomTypeId;
    private String roomTypeName;
    private double roomPrice;

    @OneToOne  // ReservationRoomType - reservation relation
    @JoinColumn(name="reservationId" , referencedColumnName = "reservationId")
    private Reservation reservation;

}
