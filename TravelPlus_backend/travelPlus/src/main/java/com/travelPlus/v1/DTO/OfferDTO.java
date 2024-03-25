package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private long offerId;
    private String offerName;
    private String discountPercentage;
    private String startDate;
    private String endDate;
    private String conditions;
    private long contractId;
}
