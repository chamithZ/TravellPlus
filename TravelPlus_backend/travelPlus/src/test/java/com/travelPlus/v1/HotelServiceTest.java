package com.travelPlus.v1;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Service.HotelService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepo hotelRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelService hotelService;

    @Test
    public void testAddHotel() {
        // Mock data
        HotelDTO hotelDTO = new HotelDTO();  // Initialize with necessary data
        Hotel hotelEntity = modelMapper.map(hotelDTO, Hotel.class);

        // Mock repository behavior
        when(hotelRepo.existsById(any())).thenReturn(false);
        when(hotelRepo.save(any(Hotel.class))).thenReturn(hotelEntity);

        // Call the service method
        String result = hotelService.addHotel(hotelDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(hotelRepo, times(1)).save(any(Hotel.class));
        verify(hotelRepo, times(1)).existsById(any());
    }

    @Test
    public void testUpdateHotel() {
        // Mock data
        HotelDTO hotelDTO = new HotelDTO();  // Initialize with necessary data

        // Mock repository behavior
        when(hotelRepo.existsById(hotelDTO.getHotelId())).thenReturn(true);

        // Call the service method
        String result = hotelService.updateHotel(hotelDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(hotelRepo, times(1)).save(any(Hotel.class));
    }

    @Test
    public void testSearchHotel() {
        // Mock data
        String destination = "TestCity";
        String checkInDate = "2024-03-06";
        String checkOutDate = "2024-03-10";
        List<Hotel> mockHotels = new ArrayList<>();  // Add mock hotels

        // Mock repository behavior
        when(hotelRepo.findHotelsByCityAndContractDates(destination, checkInDate, checkOutDate)).thenReturn(mockHotels);

        // Call the service method
        List<HotelDTO> result = hotelService.searchHotel(destination, checkInDate, checkOutDate);

        // Assertions
        assertThat(result).isNotNull();
        verify(modelMapper, times(1)).map(mockHotels, new TypeToken<ArrayList<HotelDTO>>() {}.getType());
    }

    @Test
    public void testDeleteHotel() {
        // Mock data
        long hotelId = 1L;

        // Mock repository behavior
        when(hotelRepo.existsById(hotelId)).thenReturn(true);

        // Call the service method
        String result = hotelService.deleteHotel(hotelId);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(hotelRepo, times(1)).deleteById(hotelId);
    }

    @Test
    public void testGetAllHotel() {
        // Mock data
        List<Hotel> mockHotelList = new ArrayList<>();  // Add mock hotels

        // Mock repository behavior
        when(hotelRepo.findAll()).thenReturn(mockHotelList);

        // Call the service method
        List<HotelDTO> result = hotelService.getAllHotel();

        // Assertions
        assertThat(result).isNotNull();
        verify(modelMapper, times(1)).map(mockHotelList, new TypeToken<ArrayList<HotelDTO>>() {}.getType());
    }
}
