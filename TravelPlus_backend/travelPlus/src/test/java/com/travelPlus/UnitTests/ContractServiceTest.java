package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Service.ContractService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContractServiceTest {

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractService contractService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    @Order(1)
    public void testGetContractIdByHotelIdAndDateRange() {
        when(contractRepo.findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(new Contract());

        long result = contractService.getContractIdByHotelIdAndDateRange(1L, LocalDate.now(), LocalDate.now().plusDays(1));
        assertEquals(0L, result); // Assuming you return -1 if no contract found
    }

    @Test
    @Order(2)
    public void testGetContractById() {
        when(contractRepo.findById(anyLong())).thenReturn(Optional.of(new Contract()));
        when(modelMapper.map(any(), eq(ContractDTO.class))).thenReturn(new ContractDTO());

        ContractDTO result = contractService.getContractById(1L);
        assertNotNull(result);
    }
}
