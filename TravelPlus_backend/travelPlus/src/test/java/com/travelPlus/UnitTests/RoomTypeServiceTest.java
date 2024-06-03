package com.travelPlus.UnitTests;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.RoomTypeRepo;
import com.travelPlus.v1.Repo.RoomTypeSeasonRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Service.RoomTypeService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepo roomTypeRepo;

    @Mock
    private SeasonRepo seasonRepo;

    @Mock
    private RoomTypeSeasonRepo roomTypeSeasonRepo;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomTypeService roomTypeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddRoomType() {
        // Mock data
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setRoomId(1L);
        when(roomTypeRepo.save(any(RoomType.class))).thenReturn(new RoomType());
        when(seasonRepo.findById(anyLong())).thenReturn(java.util.Optional.of(new com.travelPlus.v1.Entity.Season()));

        // Call the service method
        String result = roomTypeService.addRoomType(roomTypeDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(2)
    public void testFindAvailableRoomTypes() {
        // Mock data
        long contractId = 1L;
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(1);
        List<RoomType> roomTypes = Collections.singletonList(new RoomType());
        when(roomTypeRepo.findRoomTypesByContractIdAndSeasonValidity(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(roomTypes);

        // Call the service method
        List<RoomType> result = roomTypeService.findAvailableRoomTypes(contractId, checkInDate, checkOutDate);

        // Assert the result
        assertEquals(roomTypes.size(), result.size());
    }

    @Test
    @Order(3)
    public void testUpdateRoomType() {
        // Mock data
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setRoomId(1L);
        when(roomTypeRepo.existsById(anyLong())).thenReturn(true);
        when(roomTypeRepo.save(any(RoomType.class))).thenReturn(new RoomType());

        // Call the service method
        String result = roomTypeService.updateRoomType(roomTypeDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(4)
    public void testDeleteRoomType() {
        // Mock data
        long roomTypeId = 1L;
        when(roomTypeRepo.existsById(anyLong())).thenReturn(true);

        // Call the service method
        String result = roomTypeService.deleteRoomType(roomTypeId);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(5)
    public void testGetAllRoomType() {
        // Mock data
        long contractId = 1L;
        List<RoomType> roomTypes = Collections.singletonList(new RoomType());
        when(roomTypeRepo.findByContract_contractId(anyLong())).thenReturn(roomTypes);

        // Call the service method
        List<RoomType> result = roomTypeService.getALlRoomType(contractId);

        // Assert the result
        assertEquals(roomTypes.size(), result.size());
    }

    @Test
    @Order(6)
    public void testGetAvailableRoomCountByContractId() {
        // Mock data
        long contractId = 1L;
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(1);
        List<Object[]> availableRooms = new ArrayList<>();
        when(roomTypeRepo.findAvailableRoomTypes(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(availableRooms);

        // Call the service method
        List<Object[]> result = roomTypeService.getAvailableRoomCountByContractId(contractId, checkInDate, checkOutDate);

        // Assert the result
        assertEquals(availableRooms.size(), result.size());
    }
}

