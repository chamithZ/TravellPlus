package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.*;
import com.travelPlus.v1.Entity.*;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ReservationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReservationOffersRepo reservationOffersRepo;
    @Autowired
    private ReservationSupplementRepo supplementRepo;
    @Autowired
    private ReservationRoomTypeRepo reservationRoomTypeRepo;

    public String addBooking(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setGuestCount(reservationDTO.getGuestCount());
        reservation.setFullPayment(reservationDTO.isFullPayment());

        Hotel hotel=new Hotel();
        hotel.setHotelId(reservationDTO.getHotelId());
        reservation.setHotel(hotel);


        User user = new User();
        user.setUserId(reservationDTO.getUserId());
        reservation.setUser(user);
        reservation.setReservedDate(LocalDateTime.now());

        // Iterate over the list of RoomTypeReservationDTO
        for (RoomTypeReservationDTO roomTypeReservationDTO : reservationDTO.getRoomTypeReservation()) {
            RoomType roomType = roomTypeRepo.findById(roomTypeReservationDTO.getRoomType().getRoomId())
                    .orElseThrow(() -> new RuntimeException("RoomType not found"));

            // Create a new RoomTypeReservation
            RoomTypeReservation roomTypeReservation = new RoomTypeReservation();
            roomTypeReservation.setId(new RoomTypeReservationKey(reservation.getReservationId(), roomType.getRoomId()));
            roomTypeReservation.setReservation(reservation);
            roomTypeReservation.setRoomType(roomType);
            roomTypeReservation.setRoomsCount(roomTypeReservationDTO.getRoomCount());

            // Add the RoomTypeReservation to the Reservation's collection
            reservation.getRoomTypeReservation().add(roomTypeReservation);


            ReservationRoomType reservationRoomType=new ReservationRoomType();
            reservationRoomType.setRoomTypeName(roomType.getRoomTypeName());
            reservationRoomType.setRoomPrice(roomTypeReservationDTO.getRoomPrice());
            reservationRoomType.setReservation(reservation);

            reservationRoomTypeRepo.save(reservationRoomType);
        }
        // Save the RoomTypeReservation along with its associated RoomType
        reservationRepo.save(reservation);

        PaymentDTO paymentDTO = reservationDTO.getPaymentDTO();
        Payment payment = new Payment();
        payment.setPaymentId(paymentDTO.getPaymentId());
        // Set other payment attributes
        payment.setRmarkUpPercentage(paymentDTO.getRmarkUpPercentage());
        payment.setRcancellationDeadline(paymentDTO.getRcancellationDeadline());
        payment.setRPaymentDeadline(paymentDTO.getRPaymentDeadline());
        payment.setRcancellationPercentage(paymentDTO.getRcancellationPercentage());
        payment.setTotalPrice(paymentDTO.getTotalPrice());
        payment.setReservation(reservation);
        // Save payment
        paymentRepo.save(payment);

        paymentRepo.save(payment);
        for (ReservationSupplementDTO supplementDTO : reservationDTO.getReservationSupplementDTOS()) {
            ReservationSupplement supplement = new ReservationSupplement();
            supplement.setRSupplementId(supplementDTO.getRSupplementId());
            supplement.setPrice(supplementDTO.getPrice());
            supplement.setServiceName(supplementDTO.getServiceName());
            supplement.setReservation(reservation);
            // Set other supplement attributes
            // Save supplement
            supplementRepo.save(supplement);
        }

        // Create and save ReservationOffers
        for (ReservationOffersDTO offersDTO : reservationDTO.getReservationOffersDTOS()) {
            ReservationOffers offers = new ReservationOffers();
            offers.setROfferId(offersDTO.getROfferId());
            offers.setOfferName(offersDTO.getOfferName());
            offers.setDiscountPercentage(offersDTO.getDiscountPercentage());
            offers.setReservation(reservation);
            // Set other offers attributes
            // Save offers
            reservationOffersRepo.save(offers);
        }

        return VarList.RSP_SUCCESS;
    }



    public String updateReservation(ReservationDTO reservationDTO){
        if(reservationRepo.existsById(reservationDTO.getReservationId())){
            reservationRepo.save(modelMapper.map(reservationDTO,Reservation.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


    public String deleteReservation(long reservationId) {
        if (reservationRepo.existsById(reservationId))
        {
            reservationRepo.deleteById(reservationId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ReservationDTO> getAllReservation(long userId){

        List<Reservation> reservations=reservationRepo.findByUser_userId(userId);
        return modelMapper.map(reservations,new TypeToken<ArrayList<ReservationDTO>>(){
        }.getType());
    }


}
