package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.ReservationOffersDTO;
import com.travelPlus.v1.DTO.ReservationSupplementDTO;
import com.travelPlus.v1.Entity.ReservationOffers;
import com.travelPlus.v1.Entity.ReservationSupplement;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.ReservationOffersRepo;
import com.travelPlus.v1.Repo.ReservationRepo;
import com.travelPlus.v1.Repo.ReservationSupplementRepo;
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
public class ReservationSupplementService {
    @Autowired
    private ReservationSupplementRepo reservationSupplementRepo;
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addReservationSupplement(ReservationSupplementDTO reservationSupplementDTO){
        if(reservationSupplementRepo.existsById(reservationSupplementDTO.getRSupplementId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!reservationRepo.existsById(reservationSupplementDTO.getReservationId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            reservationSupplementRepo.save(modelMapper.map(reservationSupplementDTO, ReservationSupplement.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateReservationSupplement(ReservationSupplementDTO reservationSupplementDTO){
        if(reservationSupplementRepo.existsById(reservationSupplementDTO.getRSupplementId())){
            reservationSupplementRepo.save(modelMapper.map(reservationSupplementDTO, ReservationSupplement.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteReservationSupplement(int reservationSupplementId) {
        if (reservationSupplementRepo.existsById(reservationSupplementId))
        {
            reservationSupplementRepo.deleteById(reservationSupplementId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ReservationOffersDTO> getAllSupplements(int reservationId){
        List<ReservationSupplement> reservationOffers=reservationSupplementRepo.findByReservation_reservationId(reservationId);
        return modelMapper.map(reservationOffers,new TypeToken<ArrayList<ReservationOffersDTO>>(){
        }.getType());
    }


}
