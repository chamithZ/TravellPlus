package com.travelPlus.v1.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDTO {
    private int hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelEmail;
    private String hotelCity;
    private String hotelContactNo;
}
