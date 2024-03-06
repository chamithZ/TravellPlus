package com.travelPlus.v1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
 public class SupplementSeasonKey implements Serializable {
    private long supplementId;
    private long seasonId;


}
