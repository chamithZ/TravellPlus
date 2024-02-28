package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplementDTO {
    private int supplementId;
    private String serviceName;
    private double peakPrice;
    private double offPeakPrice;
    private int contractId;
}
