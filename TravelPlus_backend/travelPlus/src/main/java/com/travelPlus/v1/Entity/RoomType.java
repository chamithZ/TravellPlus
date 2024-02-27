package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="RoomType")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private String roomTypeName;
    private double peakRoomPrice;
    private double offPeakRoomPrice;

    private int peakNoOfRooms;
    private int offPeakNoOfRooms;
    private String roomBedType;

    private String roomDescription;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;
}
