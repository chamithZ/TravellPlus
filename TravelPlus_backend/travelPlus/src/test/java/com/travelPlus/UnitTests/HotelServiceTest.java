package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Service.HotelService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HotelServiceTest {

    @Mock
    private HotelRepo hotelRepo;

    @Mock
    private ContractRepo contractRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddHotel() throws IOException {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelId(1L);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);

        when(hotelRepo.existsById(anyLong())).thenReturn(false);
        when(modelMapper.map(any(HotelDTO.class), eq(Hotel.class))).thenReturn(hotel);
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel);

        long result = hotelService.addHotel(hotelDTO);
        assertEquals(1L, result);
    }

    @Test
    @Order(2)
    public void testUpdateHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelId(1L);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);

        when(hotelRepo.existsById(anyLong())).thenReturn(true);
        when(modelMapper.map(any(HotelDTO.class), eq(Hotel.class))).thenReturn(hotel);
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotel);

        String result = hotelService.updateHotel(hotelDTO);
        assertEquals("000", result);
    }

    @Test
    @Order(3)
    public void testSearchHotel() {
        // Mock data
        String destination = "New York";
        String checkInDate = "2024-06-01";
        String checkOutDate = "2024-06-05";
        int guestCount = 2;
        int roomCount = 1;

        // Mock repository response
        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        hotels.add(hotel);

        // Mock Page object
        Page<Hotel> page = new PageImpl<>(hotels);

        // Mock the findAvailableHotels method to return the mock Page object
        when(hotelRepo.findAvailableHotels(anyString(), any(), any(), anyInt(), anyInt(), any())).thenReturn(page);

        // Call the service method
        List<HotelDTO> result = hotelService.searchHotel(destination, checkInDate, checkOutDate, guestCount, roomCount, null);

        // Assert the result
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @Order(4)
    public void testDeleteHotel() {
        long hotelId = 1L;
        when(hotelRepo.existsById(hotelId)).thenReturn(true);
        assertEquals("001", hotelService.deleteHotel(hotelId));
    }
}
