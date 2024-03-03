package com.travelPlus.v1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RoomTypeSeasonKey implements Serializable {

    @Column(name="roomTypeId")
    int roomTypeId;
    @Column(name="seasonId")
    int seasonId;


}
