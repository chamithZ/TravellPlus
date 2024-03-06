package com.travelPlus.v1.DTO;

import com.travelPlus.v1.Entity.SupplementSeason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplementDTO {
    private long supplementId;
    private String serviceName;
    private long contractId;
    private Collection<SupplementSeasonDTO> supplementSeason=new ArrayList<>();
}
