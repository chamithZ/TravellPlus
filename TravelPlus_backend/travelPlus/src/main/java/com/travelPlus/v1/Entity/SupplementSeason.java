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
    private SupplementSeasonKey id=new SupplementSeasonKey();

    @ManyToOne
    @MapsId("supplementId")
    @JoinColumn(name="supplement_id")
    private  Supplement supplement;

    @ManyToOne
    @MapsId("seasonId")
    @JoinColumn(name="season_id")
    private Season season;
    private double price;


}
