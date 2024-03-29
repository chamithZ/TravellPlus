package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String startDate;
    private String endDate;
    private String conditions;

    @ManyToOne// offer - contract relation
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    @JsonIgnore
    private Contract contract;



}
