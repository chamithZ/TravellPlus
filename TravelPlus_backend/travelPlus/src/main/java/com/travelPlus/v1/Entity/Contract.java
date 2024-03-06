package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private long contractId;
    private String startDate;
    private String endDate;
    private double cancellationFeePrecentage;
    private int cancellationDeadline;
    private double prepaymentPrecentage;
    private int paymentDeadline;

    @OneToMany(mappedBy = "contract") // contract - roomType relation
    private List<RoomType> roomType;


    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL) // contract - offer relation
    @JsonIgnore
    private List<Offer> offers;

    @OneToMany(mappedBy = "contract")  // contract - supplement relation
    private List<Supplement> supplement;

    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    @JsonIgnore// contract - season relation
    private List<Season> seasons;

    @ManyToOne(cascade = CascadeType.DETACH)  //contract - hotel relation
    @JoinColumn(name="hotelId", referencedColumnName = "hotelId")
    private Hotel hotel;




}
