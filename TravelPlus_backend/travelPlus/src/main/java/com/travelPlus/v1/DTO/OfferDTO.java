package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private long offerId;
    private String offerName;
    private String discountPercentage;
    private String conditions;
    private long contractId;
}
