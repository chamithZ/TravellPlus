package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.RoomTypeSeason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTypeDTO {
    private long roomId;
    private String roomTypeName;
    private String roomSize;
    private String roomBedType;
    private String roomDescription;
    private String roomImage;
    private long contractId;

    private Collection<RoomTypeSeasonDTO> roomTypeSeasons=new ArrayList<>();

}
