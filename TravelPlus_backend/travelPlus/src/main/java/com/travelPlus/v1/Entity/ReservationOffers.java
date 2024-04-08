package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="ReservationOffers")

public class ReservationOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rOfferId;
    private String offerName;
    private double discountPercentage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="reservationId", referencedColumnName = "reservationId")
    private Reservation reservation;
}
