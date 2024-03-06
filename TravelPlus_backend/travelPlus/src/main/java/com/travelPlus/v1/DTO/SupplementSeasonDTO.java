package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Entity.Supplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplementSeasonDTO {
    private Supplement supplement;
    private Season season;
    private double price;
}
