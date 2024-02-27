package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private String paymentMethod;
    private String reservedRoomType;
    private double totalPrice;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationOffers>  reservationOffers;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationSupplement>  reservationSupplements;

}
