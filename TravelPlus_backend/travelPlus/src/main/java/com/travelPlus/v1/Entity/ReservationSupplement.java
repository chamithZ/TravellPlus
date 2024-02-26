package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="ReservationSupplement")
public class ReservationSupplement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rSupplementId;
    private double price;
    private String serviceName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="reservationId", referencedColumnName = "reservationId")
    private Hotel hotel;


}
