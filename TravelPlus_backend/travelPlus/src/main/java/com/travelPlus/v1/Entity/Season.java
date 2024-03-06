package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="Season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long seasonId;
    String seasonType;
    String startDate;
    String endDate;
    double markUp;



    @OneToMany(mappedBy = "season")
    @JsonIgnore
    Collection<RoomTypeSeason> roomTypeSeasons=new ArrayList<>();

    @OneToMany(mappedBy = "season")
    Collection<SupplementSeason> supplementSeasons=new ArrayList<>();


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    private Contract contract;
}
