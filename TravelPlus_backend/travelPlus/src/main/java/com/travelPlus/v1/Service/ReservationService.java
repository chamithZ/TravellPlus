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
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private  UserRepo userRepo;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ReservationOffersRepo reservationOffersRepo;
    @Autowired
    private ReservationSupplementRepo supplementRepo;
    @Autowired
    private ReservationRoomTypeRepo reservationRoomTypeRepo;
    ExecutorService emailExecutorService = Executors.newSingleThreadExecutor();

    public String addBooking(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setGuestCount(reservationDTO.getGuestCount());
        reservation.setFullPayment(reservationDTO.getIsFullPayment());
        reservation.setReservationStatus(true);

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
        System.out.println(paymentDTO.getRmarkUpPercentage());
        payment.setRmarkUpPercentage(paymentDTO.getRmarkUpPercentage());
        payment.setRcancellationDeadline(paymentDTO.getRcancellationDeadline());
        payment.setRPaymentDeadline(paymentDTO.getRPaymentDeadline());
        payment.setRprepaymentPercentage(paymentDTO.getRprepaymentPercentage());
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
        emailExecutorService.submit(() -> {
            User guest = userRepo.findById(reservationDTO.getUserId()).orElse(null);
            if (guest != null) {
                StringBuilder body = new StringBuilder();
                body.append("Dear ").append(guest.getName()).append(",\n\n");
                body.append("Thank you for choosing TravelPlus!\n\n");
                body.append("Your reservation has been confirmed. Below are the details:\n\n");
                body.append("Reservation ID: ").append(reservation.getReservationId()).append("\n");
                body.append("Check-in Date: ").append(reservation.getCheckInDate()).append("\n");
                body.append("Check-out Date: ").append(reservation.getCheckOutDate()).append("\n");
                body.append("Number of Guests: ").append(reservation.getGuestCount()).append("\n\n");
                body.append("We look forward to welcoming you!\n\n");
                body.append("Best regards,\n");
                body.append("The TravelPlus Team");

                EmailService.sendEmail(guest.getEmail(), "TravelPlus Reservation Confirmation", body.toString());
            }
        });

        return VarList.RSP_SUCCESS;
    }

    public UserReservationDTO getReservationById(long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepo.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            return modelMapper.map(reservation, UserReservationDTO.class);
        } else {
            return null;
        }
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
            Reservation reservation =reservationRepo.findById(reservationId).orElse(null);
            assert reservation != null;
            reservation.setReservationStatus(false);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<UserReservationDTO> getAllReservation(long userId){

        List<Reservation> reservations=reservationRepo.findByUser_userId(userId);
        return modelMapper.map(reservations,new TypeToken<ArrayList<UserReservationDTO>>(){
        }.getType());
    }
    public String updateIsFullPayment(long reservationId) {
        Optional<Reservation> optionalReservation = reservationRepo.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setFullPayment(true); // Set isFullPayment to true
            reservationRepo.save(reservation);
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
    public List<UserReservationDTO> getReservationsByHotelId(long hotelId) {
        try {
            List<Reservation> reservations = reservationRepo.findByHotel_HotelId(hotelId);

            // Initialize ModelMapper
            ModelMapper modelMapper = new ModelMapper();

            // Define the TypeToken
            java.lang.reflect.Type targetListType = new TypeToken<List<UserReservationDTO>>() {}.getType();

            // Perform mapping
            List<UserReservationDTO> userReservationDTOs = modelMapper.map(reservations, targetListType);
            return userReservationDTOs;
        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception
            return null; // or handle the exception appropriately
        }
    }

}
