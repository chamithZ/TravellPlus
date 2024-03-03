package com.travelPlus.v1.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SupplementSeason {
    @EmbeddedId
    private SupplementSeasonKey id;

    @ManyToOne
    @MapsId("supplementId")

    Supplement supplement;

    @ManyToOne
    @MapsId("seasonId")
    Season season;
    private double price;


}
