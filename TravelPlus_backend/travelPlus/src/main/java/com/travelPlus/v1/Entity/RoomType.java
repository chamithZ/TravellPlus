package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="RoomType")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    private String roomTypeName;
    private int roomSize;
    private String roomBedType;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String roomDescription;
    private String roomImage;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="contractId", referencedColumnName = "contractId")
    @JsonIgnore
    private Contract contract;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    private Collection<RoomTypeSeason> roomTypeSeasons=new ArrayList<>();

    @OneToMany(mappedBy = "roomType",cascade = CascadeType.ALL)
    private Collection<RoomTypeReservation> roomTypeReservation=new ArrayList<>();

}
