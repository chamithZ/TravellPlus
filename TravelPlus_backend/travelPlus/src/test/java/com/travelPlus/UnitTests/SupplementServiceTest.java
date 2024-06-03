package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.SupplementDTO;
import com.travelPlus.v1.Entity.Supplement;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Repo.SupplementRepo;
import com.travelPlus.v1.Service.SupplementService;
import com.travelPlus.v1.Utill.VarList;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SupplementServiceTest {

    @Mock
    private SupplementRepo supplementRepo;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private SeasonRepo seasonRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SupplementService supplementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddSupplement() {
        // Mock data
        SupplementDTO supplementDTO = new SupplementDTO();
        supplementDTO.setSupplementId(1L);
        supplementDTO.setContractId(1L);

        when(supplementRepo.existsById(anyLong())).thenReturn(false);
        when(contractRepo.existsById(anyLong())).thenReturn(true);
        when(supplementRepo.save(any(Supplement.class))).thenReturn(new Supplement());

        // Call the service method
        String result = supplementService.addSupplement(supplementDTO);

        // Assert the result
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    @Order(2)
    public void testUpdateSupplement() {
        // Mock data
        SupplementDTO supplementDTO = new SupplementDTO();
        supplementDTO.setSupplementId(1L);

        when(supplementRepo.existsById(anyLong())).thenReturn(true);
        when(supplementRepo.save(any(Supplement.class))).thenReturn(new Supplement());

        // Call the service method
        String result = supplementService.updateSupplement(supplementDTO);

        // Assert the result
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    @Order(3)
    public void testDeleteSupplement() {
        // Mock data
        long supplementId = 1L;

        when(supplementRepo.existsById(supplementId)).thenReturn(true);

        // Call the service method
        String result = supplementService.deleteSupplement(supplementId);

        // Assert the result
        assertEquals(VarList.RSP_SUCCESS, result);
    }


}
