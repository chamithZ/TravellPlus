package com.travelPlus.v1.Entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RoomTypeSeasonKey implements Serializable {
    long roomId;
    long seasonId;
}
