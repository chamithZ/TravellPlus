package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepo hotelRepo;

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

    public String deleteHotel(int hotelId) {
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
}
