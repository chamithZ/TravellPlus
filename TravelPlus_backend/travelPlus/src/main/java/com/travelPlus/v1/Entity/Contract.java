package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;
    private String startDate;
    private String endDate;
    private double cancellationFeePrecentage;
    private int cancellationDeadline;
    private double prepaymentPrecentage;
    private int paymentDeadline;

    @OneToMany(mappedBy = "contract") // contract - roomType relation
    private List<RoomType> roomType;


    @OneToMany(mappedBy = "contract") // contract - offer relation
    private List<Offer> offer;

    @OneToMany(mappedBy = "contract")  // contract - supplement relation
    private List<Supplement> supplement;

    @OneToMany(mappedBy = "contract")  // contract - season relation
    private List<Season> seasons;

    @ManyToOne(cascade = CascadeType.DETACH)  //contract - hotel relation
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;




}
