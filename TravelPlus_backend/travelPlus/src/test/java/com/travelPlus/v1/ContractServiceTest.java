package com.travelPlus.v1;

import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.DTO.OfferDTO;
import com.travelPlus.v1.DTO.SeasonDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.OfferRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Service.ContractService;
import com.travelPlus.v1.Utill.VarList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContractServiceTest {

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private HotelRepo hotelRepo;

    @Mock
    private SeasonRepo seasonRepo;

    @Mock
    private OfferRepo offerRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractService contractService;

    @Test
    public void testAddContract() {
        // Mock data
        ContractDTO contractDTO = createSampleContractDTO();

        // Mock repository behavior
        when(contractRepo.existsById(contractDTO.getContractId())).thenReturn(false);
        when(hotelRepo.existsById(contractDTO.getHotelId())).thenReturn(true);

        // Call the service method
        String result = contractService.addContract(contractDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(contractRepo, times(1)).save(any(Contract.class));
        verify(seasonRepo, times(contractDTO.getSeason().size())).save(any(Season.class));
        verify(offerRepo, times(contractDTO.getOffer().size())).save(any(Offer.class));
    }

    @Test
    public void testUpdateContract() {
        // Mock data
        ContractDTO contractDTO = createSampleContractDTO();

        // Mock repository behavior
        when(contractRepo.existsById(contractDTO.getContractId())).thenReturn(true);

        // Call the service method
        String result = contractService.updateContract(contractDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(contractRepo, times(1)).save(any(Contract.class));
    }

    @Test
    public void testDeleteContract() {
        // Mock data
        long contractId = 1L;

        // Mock repository behavior
        when(contractRepo.existsById(contractId)).thenReturn(true);

        // Call the service method
        String result = contractService.deleteContract(contractId);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(contractRepo, times(1)).deleteById(contractId);
    }

    @Test
    public void testGetAllContracts() {
        // Mock data
        List<Contract> mockContractList = new ArrayList<>();  // Add mock contracts

        // Mock repository behavior
        when(contractRepo.findAll()).thenReturn(mockContractList);

        // Call the service method
        List<ContractDTO> result = contractService.getAllContracts();

        // Assertions
        assertThat(result).isNotNull();
        verify(modelMapper, times(1)).map(mockContractList, ArrayList.class);
    }

    private ContractDTO createSampleContractDTO() {
        // Create a sample ContractDTO for testing
        ContractDTO contractDTO = new ContractDTO();
        // Set other fields as needed
        // ...

        // Set Seasons
        List<SeasonDTO> seasons = new ArrayList<>();
        seasons.add(new SeasonDTO(1L, "Summer", "2024-06-01", "2024-08-31", 0.1, 1L));
        seasons.add(new SeasonDTO(2L, "Winter", "2024-12-01", "2024-02-28", 0.2, 2L));
        contractDTO.setSeason(seasons);

        // Set Offers
        List<OfferDTO> offers = new ArrayList<>();
        offers.add(new OfferDTO(1L,"11 11", "Promotion", "valid only for new users", 1L));
        offers.add(new OfferDTO(2L,"New User", "Promotion", "valid only for this year registered customers", 2L));
        contractDTO.setOffer(offers);

        return contractDTO;
    }

}
