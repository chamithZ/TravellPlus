package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeasonDTO {
    long seasonId;
    String seasonType;
    LocalDate startDate;
    LocalDate endDate;
    double markUp;
    long contractId;

}
