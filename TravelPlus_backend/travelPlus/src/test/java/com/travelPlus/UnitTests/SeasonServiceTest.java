package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.SeasonDTO;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Service.SeasonService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeasonServiceTest {

    @Mock
    private SeasonRepo seasonRepo;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SeasonService seasonService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddSeason() {
        // Mock data
        SeasonDTO seasonDTO = new SeasonDTO();
        seasonDTO.setSeasonId(1L);

        when(seasonRepo.existsById(anyLong())).thenReturn(false);
        when(contractRepo.existsById(anyLong())).thenReturn(true);
        when(seasonRepo.save(any(Season.class))).thenReturn(new Season());

        // Call the service method
        String result = seasonService.addSeason(seasonDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(2)
    public void testUpdateSeason() {
        // Mock data
        SeasonDTO seasonDTO = new SeasonDTO();
        seasonDTO.setSeasonId(1L);

        when(seasonRepo.existsById(anyLong())).thenReturn(true);
        when(seasonRepo.save(any(Season.class))).thenReturn(new Season());

        // Call the service method
        String result = seasonService.updateSeason(seasonDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(3)
    public void testDeleteSeason() {
        // Mock data
        long seasonId = 1L;

        when(seasonRepo.existsById(anyLong())).thenReturn(true);

        // Call the service method
        String result = seasonService.deleteSeason(seasonId);

        // Assert the result
        assertEquals("000", result);
    }


    @Test
    @Order(5)
    public void testFindSeasonById() {
        // Mock data
        long seasonId = 1L;
        Season season = new Season();
        when(seasonRepo.findById(anyLong())).thenReturn(Optional.of(season));

        // Call the service method
        Season result = seasonService.findSeasonById(seasonId);

        // Assert the result
        assertEquals(season, result);
    }

    @Test
    @Order(6)
    public void testFindSeasonByContract() {
        // Mock data
        long contractId = 1L;
        String checkIn = LocalDate.now().toString();
        String checkOut = LocalDate.now().plusDays(1).toString();
        Season season = new Season();
        when(seasonRepo.findSeasonByDates(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Optional.of(season));

        // Call the service method
        Season result = seasonService.findSeasonByContract(contractId, checkIn, checkOut);

        // Assert the result
        assertEquals(season, result);
    }
}
