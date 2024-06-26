package com.travelPlus.v1.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDTO {
    private long hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelEmail;
    private String hotelCity;
    private String hotelContactNo;
    private String hotelDescription;
    private List<HotelImageDTO> hotelImages;
}
