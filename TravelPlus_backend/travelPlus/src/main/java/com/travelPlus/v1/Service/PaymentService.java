package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.PaymentDTO;
import com.travelPlus.v1.DTO.ReservationOffersDTO;
import com.travelPlus.v1.DTO.ReservationSupplementDTO;
import com.travelPlus.v1.Entity.Payment;
import com.travelPlus.v1.Entity.ReservationOffers;
import com.travelPlus.v1.Entity.ReservationSupplement;
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
public class PaymentService {

        @Autowired
        private PaymentRepo paymentRepo;
        @Autowired
        private ReservationRepo reservationRepo;

        @Autowired
        private ModelMapper modelMapper;

        public String addPayment(PaymentDTO paymentDTO){
            if(paymentRepo.existsById(paymentDTO.getPaymentId())){
                return VarList.RSP_DUPLICATED;
            }
            else if(!reservationRepo.existsById(paymentDTO.getReservationId())){
                return VarList.RSP_NotAvailable;
            }
            else{
                paymentRepo.save(modelMapper.map(paymentDTO, Payment.class));
                return VarList.RSP_SUCCESS;
            }
        }

        public String updatePayment(PaymentDTO paymentDTO){
            if(paymentRepo.existsById(paymentDTO.getPaymentId())){
                paymentRepo.save(modelMapper.map(paymentDTO, Payment.class));
                return VarList.RSP_SUCCESS;
            }
            else{
                return VarList.RSP_NO_DATA_FOUND;
            }
        }

        public String deletePayment(long paymentId) {
            if (paymentRepo.existsById(paymentId))
            {
                paymentRepo.deleteById(paymentId);
                return VarList.RSP_SUCCESS;
            }
            else{
                return VarList.RSP_NO_DATA_FOUND;
            }
        }
    public PaymentDTO getPaymentsByReservationId(long reservationId) {

        Payment payment=paymentRepo.findByReservation_ReservationId(reservationId).orElse(null);
        return modelMapper.map(payment,PaymentDTO.class);
    }




}


