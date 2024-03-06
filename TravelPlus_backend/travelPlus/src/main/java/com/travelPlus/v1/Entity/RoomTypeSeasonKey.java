package com.travelPlus.v1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RoomTypeSeasonKey implements Serializable {

    @Column(name="roomId")
    long roomId;
    @Column(name="seasonId")
    long seasonId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypeSeasonKey that = (RoomTypeSeasonKey) o;
        return roomId == that.roomId &&
                seasonId == that.seasonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, seasonId);
    }




}
