package com.travelPlus.v1.Entity;

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
    private int reservationId;
    private String checkInDate;
    private String checkOutDate;
    private int childCount;
    private int adultCount;
    boolean isFullPayment;




    @OneToMany(mappedBy = "reservation") //reservation - reservationOffers relation
    private List<ReservationOffers>  reservationOffers;

    @OneToMany(mappedBy = "reservation")  //reservation- reservationSupplement relation
    private List<ReservationSupplement>  reservationSupplements;

    @ManyToOne(cascade = CascadeType.DETACH) //reservation - user relation
    @JoinColumn(name="userId" , referencedColumnName = "userId")
    private User user;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    @OneToOne(mappedBy = "reservation")
    private ReservationRoomType reservationRoomType;

    @ManyToOne
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    private RoomType roomType;





}
