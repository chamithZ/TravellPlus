package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
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
    private long supplementId;
    private String serviceName;


    @ManyToOne
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    private Contract contract;

    @OneToMany(mappedBy = "supplement",cascade = CascadeType.ALL)
    Collection<SupplementSeason> supplementSeason=new ArrayList<>();

}
