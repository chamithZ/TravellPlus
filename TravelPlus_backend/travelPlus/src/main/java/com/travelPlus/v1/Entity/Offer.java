package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerId;
    private String offerName;
    private String discountPrecentage;
    private String conditions;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hotelId", referencedColumnName = "hotelID")
    private Hotel hotel;

}
