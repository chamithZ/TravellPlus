package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelEmail;
    private String hotelCity;
    private String hotelContactNo;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String hotelDescription;
    private boolean hotelStatus;

    @OneToMany(mappedBy = "hotel")
    private List<Contract> contract;

    @OneToMany(mappedBy = "hotel")
    private List<HotelImage> hotelImages;

    @OneToMany(mappedBy = "hotel")
    private List<Reservation> reservations;


}
