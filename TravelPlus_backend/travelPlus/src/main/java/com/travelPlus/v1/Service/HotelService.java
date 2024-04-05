package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private SeasonRepo seasonRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addHotel(HotelDTO hotelDTO){
        if(hotelRepo.existsById(hotelDTO.getHotelId())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            hotelRepo.save(modelMapper.map(hotelDTO, Hotel.class));
            return VarList.RSP_SUCCESS;
        }
    }
    public String updateHotel(HotelDTO hotelDTO){
        if(hotelRepo.existsById(hotelDTO.getHotelId())){
            hotelRepo.save(modelMapper.map(hotelDTO,Hotel.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<HotelDTO> searchHotel(String destination, String checkInDate, String checkOutDate, int guestCount, int roomCount) {

        int guestsPerRoom=guestCount/roomCount;
        // Parse string dates to LocalDate

        LocalDate parsedCheckInDate = LocalDate.parse(checkInDate);
        LocalDate parsedCheckOutDate = LocalDate.parse(checkOutDate);


        // Call repository method to find available hotels
        List<Hotel> hotels = hotelRepo.findAvailableHotels(destination, parsedCheckInDate, parsedCheckOutDate, guestsPerRoom, roomCount);

        // Map Hotel entities to HotelDTOs

        return modelMapper.map(hotels, new TypeToken<ArrayList<HotelDTO>>() {}.getType());
    }


    public String deleteHotel(long hotelId) {
        if (hotelRepo.existsById(hotelId))
        {
            hotelRepo.deleteById(hotelId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<HotelDTO> getAllHotel(){
        List<Hotel> hotelList=hotelRepo.findAll();
        return modelMapper.map(hotelList,new TypeToken<ArrayList<HotelDTO>>(){
        }.getType());
    }
    public HotelDTO getHotel(long hotelId) {
        if(hotelRepo.existsById(hotelId)){
            Hotel hotel=hotelRepo.findById(hotelId).orElse(null);
            return modelMapper.map(hotel,HotelDTO.class);
        }
        else{
            return null;
        }
    }
}
