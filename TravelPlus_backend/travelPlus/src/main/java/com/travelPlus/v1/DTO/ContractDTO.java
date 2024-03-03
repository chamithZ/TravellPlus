package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Entity.Supplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContractDTO {
    private int contractId;
    private String startDate;
    private String endDate;
    private double cancellationFeePrecentage;
    private int cancellationDeadline;
    private double prepaymentPrecentage;
    private int paymentDeadline;
    private int hotelId;


}


