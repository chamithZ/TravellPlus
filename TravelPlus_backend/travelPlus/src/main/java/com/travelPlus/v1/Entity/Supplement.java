package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Supplement")
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplementId;
    private String serviceName;
    private double peakPrice;
    private double offPeakPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;

}
