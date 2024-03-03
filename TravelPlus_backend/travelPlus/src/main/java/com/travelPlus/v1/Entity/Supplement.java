package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    private Contract contract;

    @OneToMany(mappedBy = "supplement")
    Set<SupplementSeason> supplementSeason;

}
