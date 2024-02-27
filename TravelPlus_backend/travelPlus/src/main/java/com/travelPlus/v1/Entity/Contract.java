package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;
    private String startDate;
    private String endDate;
    private String peakMarkUp;
    private String offPeakMarkUp;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;


}
