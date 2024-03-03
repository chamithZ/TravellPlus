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
@Table(name="Season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seasonId;
    String seasonType;
    String startDate;
    String endDate;
    double markUp;



    @OneToMany(mappedBy = "season")
    Set<RoomTypeSeason> roomTypeSeasons;

    @OneToMany(mappedBy = "season")
    Set<SupplementSeason> supplementSeasons;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    private Contract contract;
}
