package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.ReservationOffersDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Entity.ReservationOffers;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReservationOfferService {
    @Autowired
    private ReservationOffersRepo reservationOfferRepo;
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addReservationOffer(ReservationOffersDTO reservationOffersDTO){
        if(reservationOfferRepo.existsById(reservationOffersDTO.getROfferId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!reservationRepo.existsById(reservationOffersDTO.getReservationId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            reservationOfferRepo.save(modelMapper.map(reservationOffersDTO, ReservationOffers.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateReservationOffer(ReservationOffersDTO reservationOffersDTO){
        if(reservationOfferRepo.existsById(reservationOffersDTO.getROfferId())){
            reservationOfferRepo.save(modelMapper.map(reservationOffersDTO, ReservationOffers.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteRoomType(int reservationOfferId) {
        if (reservationOfferRepo.existsById(reservationOfferId))
        {
            reservationOfferRepo.deleteById(reservationOfferId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ReservationOffersDTO> getAllOffers(int reservationId){
        List<ReservationOffers> reservationOffers=reservationOfferRepo.findByReservation_reservationId(reservationId);
        return modelMapper.map(reservationOffers,new TypeToken<ArrayList<ReservationOffersDTO>>(){
        }.getType());
    }


}
