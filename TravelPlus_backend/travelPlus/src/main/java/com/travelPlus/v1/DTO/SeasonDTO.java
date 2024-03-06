package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeasonDTO {
    long seasonId;
    String seasonType;
    String startDate;
    String endDate;
    double markUp;
    long contractId;

}
