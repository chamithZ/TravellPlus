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


    @ManyToMany
    @JoinTable(
            name="room_reservation",
            joinColumns=@JoinColumn(name="reservationId"),
            inverseJoinColumns=@JoinColumn(name="roomId")
    )
    private List<RoomType> reservationRoomTypes;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationOffers>  reservationOffers;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationSupplement>  reservationSupplements;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="userId" , referencedColumnName = "userId")
    private User user;




}
