package com.travelPlus.v1.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private  Supplement supplement;

    @ManyToOne
    @MapsId("seasonId")
    @JoinColumn(name="season_id")
    @JsonIgnore
    private Season season;
    private double price;


}
