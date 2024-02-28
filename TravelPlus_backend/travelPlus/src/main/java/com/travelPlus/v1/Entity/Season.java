package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seasonId;
    String seasonType;
    String startDate;
    String endDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    private Contract contract;
}
