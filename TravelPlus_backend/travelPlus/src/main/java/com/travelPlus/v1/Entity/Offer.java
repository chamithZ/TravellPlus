package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private long offerId;
    private String offerName;
    private String discountPercentage;
    private String conditions;

    @ManyToOne// offer - contract relation
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    @JsonBackReference
    private Contract contract;



}
