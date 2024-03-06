package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;
    private String checkInDate;
    private String checkOutDate;
    private int childCount;
    private int adultCount;
    boolean isFullPayment;




    @OneToMany(mappedBy = "reservation") //reservation - reservationOffers relation
    @JsonIgnore
    private List<ReservationOffers>  reservationOffers;

    @OneToMany(mappedBy = "reservation")  //reservation- reservationSupplement relation
    @JsonIgnore
    private List<ReservationSupplement>  reservationSupplements;

    @ManyToOne(cascade = CascadeType.DETACH) //reservation - user relation
    @JsonIgnore
    @JoinColumn(name="userId" , referencedColumnName = "userId")
    private User user;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JsonIgnore
    private ReservationRoomType reservationRoomType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    private RoomType roomType;





}
