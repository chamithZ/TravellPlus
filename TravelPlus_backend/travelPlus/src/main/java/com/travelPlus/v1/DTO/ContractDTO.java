package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Entity.RoomType;
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
    private long contractId;
    private String startDate;
    private String endDate;
    private double cancellationFeePercentage;
    private int cancellationDeadline;
    private double prepaymentPercentage;
    private int paymentDeadline;
    private long hotelId;
    private List<OfferDTO> offer;
    private List<SupplementDTO> supplements;
    private List<SeasonDTO> season;
    private List<RoomTypeDTO> roomType;


}



