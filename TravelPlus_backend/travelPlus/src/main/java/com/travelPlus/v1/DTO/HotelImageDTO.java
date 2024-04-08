package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelImageDTO {
    private long imageId;
    private  String imageName;
    private  String imagePath;
    private byte[] imageData;
}
