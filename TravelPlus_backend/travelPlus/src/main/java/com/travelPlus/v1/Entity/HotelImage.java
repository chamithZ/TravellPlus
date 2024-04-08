package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="HotelImage")
public class HotelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;
    private  String imageName;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;

    @ManyToOne(cascade = CascadeType.DETACH)  //hotelImages - hotel relation
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;
}
