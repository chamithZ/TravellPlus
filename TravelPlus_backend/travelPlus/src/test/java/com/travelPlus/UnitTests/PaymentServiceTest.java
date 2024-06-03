package com.travelPlus.UnitTests;
import com.travelPlus.v1.DTO.PaymentDTO;
import com.travelPlus.v1.Entity.Payment;
import com.travelPlus.v1.Repo.PaymentRepo;
import com.travelPlus.v1.Repo.ReservationRepo;
import com.travelPlus.v1.Service.PaymentService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddPayment() {
        // Mock data
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(1L);

        when(paymentRepo.existsById(anyLong())).thenReturn(false);
        when(reservationRepo.existsById(anyLong())).thenReturn(true);
        when(paymentRepo.save(any(Payment.class))).thenReturn(new Payment());

        // Call the service method
        String result = paymentService.addPayment(paymentDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(2)
    public void testUpdatePayment() {
        // Mock data
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(1L);

        when(paymentRepo.existsById(anyLong())).thenReturn(true);
        when(paymentRepo.save(any(Payment.class))).thenReturn(new Payment());

        // Call the service method
        String result = paymentService.updatePayment(paymentDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(4)
    public void testDeletePayment() {
        // Mock data
        long paymentId = 1L;

        when(paymentRepo.existsById(anyLong())).thenReturn(true);

        // Call the service method
        String result = paymentService.deletePayment(paymentId);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(3)
    public void testGetPaymentsByReservationId() {
        // Mock data
        long reservationId = 1L;
        Payment payment = new Payment();
        when(paymentRepo.findByReservation_ReservationId(anyLong())).thenReturn(Optional.of(payment));
        when(modelMapper.map(any(Payment.class), any())).thenReturn(new PaymentDTO());

        // Call the service method
        PaymentDTO result = paymentService.getPaymentsByReservationId(reservationId);

        // Assert the result
        assertEquals(new PaymentDTO(), result);
    }
}
