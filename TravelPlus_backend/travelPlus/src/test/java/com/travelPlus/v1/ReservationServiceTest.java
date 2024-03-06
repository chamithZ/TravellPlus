package com.travelPlus.v1;


import com.travelPlus.v1.DTO.PaymentDTO;
import com.travelPlus.v1.DTO.ReservationDTO;
import com.travelPlus.v1.Entity.Payment;
import com.travelPlus.v1.Entity.Reservation;
import com.travelPlus.v1.Entity.ReservationRoomType;
import com.travelPlus.v1.Repo.PaymentRepo;
import com.travelPlus.v1.Repo.ReservationRepo;
import com.travelPlus.v1.Repo.ReservationRoomTypeRepo;
import com.travelPlus.v1.Service.ReservationService;
import com.travelPlus.v1.Utill.VarList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private ReservationRoomTypeRepo reservationRoomTypeRepo;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testAddReservation() {
        // Mock data
        ReservationDTO reservationDTO = new ReservationDTO();  // Initialize with necessary data

        // Mock repository behavior
        when(reservationRepo.existsById(any())).thenReturn(false);

        // Mock mapper behavior
        when(modelMapper.map(any(ReservationDTO.class), eq(Reservation.class))).thenReturn(new Reservation());

        // Call the service method
        String result = reservationService.addReservation(reservationDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);  // Adjust based on your expected response
        verify(reservationRepo, times(1)).save(any(Reservation.class));

        // Verify that paymentRepo.save() is invoked when the paymentDTO is not null
        if (reservationDTO.getPaymentDTO() != null) {
            verify(paymentRepo, times(1)).save(any(Payment.class));
        } else {
            // If paymentDTO is null, verify that paymentRepo.save() is never called
            verify(paymentRepo, never()).save(any(Payment.class));
        }

        // Verify that reservationRoomTypeRepo.save() is invoked when the reservationRoomType is not null
        if (reservationDTO.getReservationRoomType() != null) {
            verify(reservationRoomTypeRepo, times(1)).save(any(ReservationRoomType.class));
        } else {
            // If reservationRoomType is null, verify that reservationRoomTypeRepo.save() is never called
            verify(reservationRoomTypeRepo, never()).save(any(ReservationRoomType.class));
        }
    }


    @Test
    public void testUpdateReservation() {
        // Mock data
        ReservationDTO reservationDTO = new ReservationDTO();  // Initialize with necessary data

        // Mock repository behavior
        when(reservationRepo.existsById(any())).thenReturn(true);

        // Mock mapper behavior
        when(modelMapper.map(any(ReservationDTO.class), eq(Reservation.class))).thenReturn(new Reservation());

        // Call the service method
        String result = reservationService.updateReservation(reservationDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);  // Adjust based on your expected response
        verify(reservationRepo, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation() {
        // Mock data
        long reservationId = 1L;

        // Mock repository behavior
        when(reservationRepo.existsById(reservationId)).thenReturn(true);

        // Call the service method
        String result = reservationService.deleteReservation(reservationId);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);  // Adjust based on your expected response
        verify(reservationRepo, times(1)).deleteById(reservationId);
    }

    @Test
    public void testGetAllReservation() {
        // Mock data
        long userId = 1L;
        List<Reservation> mockReservations = new ArrayList<>();  // Add mock reservations

        // Mock repository behavior
        when(reservationRepo.findByUser_userId(userId)).thenReturn(mockReservations);

        // Mock mapper behavior
        when(modelMapper.map(mockReservations, new TypeToken<ArrayList<ReservationDTO>>() {}.getType()))
                .thenReturn(new ArrayList<>());

        // Call the service method
        List<ReservationDTO> result = reservationService.getAllReservation(userId);

        // Assertions
        assertThat(result).isNotNull();  // Adjust based on your expected response
        verify(modelMapper, times(1))
                .map(mockReservations, new TypeToken<ArrayList<ReservationDTO>>() {}.getType());
    }
}
