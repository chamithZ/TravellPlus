package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContractDTO {
    private int contractId;
    private String startDate;
    private String endDate;
    private String peakMarkUp;
    private String offPeakMarkUp;
    private int hotelId;

}


