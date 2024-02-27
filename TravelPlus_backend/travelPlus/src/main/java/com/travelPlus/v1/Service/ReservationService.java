package com.travelPlus.v1.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.ReservationDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Repo.ReservationRepo;
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

    public String addReservation(ReservationDTO reservationDTO){
        if(reservationRepo.existsById(reservationDTO.getReservationId())){
            return VarList.RSP_DUPLICATED;
        }
        else{

            reservationRepo.save(modelMapper.map(reservationDTO, Reservation.class));
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


    public String deleteReservation(int reservationId) {
        if (reservationRepo.existsById(reservationId))
        {
            reservationRepo.deleteById(reservationId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ReservationDTO> getAllReservation(){
        List<Reservation> reservations=reservationRepo.findAll();
        return modelMapper.map(reservations,new TypeToken<ArrayList<ReservationDTO>>(){
        }.getType());
    }
}
