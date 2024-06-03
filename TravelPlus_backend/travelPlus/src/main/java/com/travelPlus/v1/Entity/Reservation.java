package com.travelPlus.v1.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime reservedDate;
    private int guestCount;
    private boolean isFullPayment;
    private boolean reservationStatus;




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

    @ManyToOne(cascade = CascadeType.DETACH) //reservation - hotel relation
    @JoinColumn(name="hotelId" , referencedColumnName = "hotelId")
    @JsonIgnore
    private Hotel hotel;


    @OneToMany(mappedBy = "reservation")
    @JsonIgnore
    private List<ReservationRoomType> reservationRoomTypes;

    @OneToMany(mappedBy = "reservation",cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<RoomTypeReservation> roomTypeReservation=new ArrayList<>();

}
