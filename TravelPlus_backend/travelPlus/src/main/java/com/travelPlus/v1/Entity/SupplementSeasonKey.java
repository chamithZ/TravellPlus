package com.travelPlus.v1.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
 class SupplementSeasonKey implements Serializable {
    @Column(name="supplementId")
    int supplementId;
    @Column(name="seasonId")
    int seasonId;


}
