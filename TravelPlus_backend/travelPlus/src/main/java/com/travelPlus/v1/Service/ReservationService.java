package com.travelPlus.v1.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.PaymentDTO;
import com.travelPlus.v1.DTO.ReservationDTO;
import com.travelPlus.v1.Entity.*;
import com.travelPlus.v1.Repo.PaymentRepo;
import com.travelPlus.v1.Repo.ReservationRepo;
import com.travelPlus.v1.Repo.ReservationRoomTypeRepo;
import com.travelPlus.v1.Repo.RoomTypeRepo;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

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
    private PaymentRepo paymentRepo;
    @Autowired
    private ReservationRoomTypeRepo reservationRoomTypeRepo;

    public String addReservation(ReservationDTO reservationDTO) {
        if (reservationRepo.existsById(reservationDTO.getReservationId())) {
            return VarList.RSP_DUPLICATED;
        } else {
            Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);

            reservationRepo.save(reservation);

            // If paymentDTO is present in reservationDTO, map it to Payment entity and save
            if (reservationDTO.getPaymentDTO() != null) {
                Payment payment = modelMapper.map(reservationDTO.getPaymentDTO(), Payment.class);
                payment.setReservation(reservation);
                paymentRepo.save(payment);
            }
            if (reservationDTO.getReservationRoomType() != null) {
                ReservationRoomType reservationRoomType = modelMapper.map(reservationDTO.getReservationRoomType(), ReservationRoomType.class);
                reservationRoomType.setReservation(reservation);
                reservationRoomTypeRepo.save(reservationRoomType);
            }

            return VarList.RSP_SUCCESS;
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
