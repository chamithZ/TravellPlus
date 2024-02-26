package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO {
    private int offerId;
    private String offerName;
    private String discountPrecentage;
    private String conditions;
    private int HotelId;
}
