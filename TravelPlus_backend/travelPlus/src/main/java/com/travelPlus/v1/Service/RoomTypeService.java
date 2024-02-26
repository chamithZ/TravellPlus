package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.RoomTypeRepo;
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
public class RoomTypeService {
    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addRoomType(RoomTypeDTO roomDTO){
        if(roomTypeRepo.existsById(roomDTO.getRoomId())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            roomTypeRepo.save(modelMapper.map(roomDTO, RoomType.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateRoomType(RoomTypeDTO roomTypeDTO){
        if(roomTypeRepo.existsById(roomTypeDTO.getRoomId())){
            roomTypeRepo.save(modelMapper.map(roomTypeDTO,RoomType.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteRoomType(int roomTypeId) {
        if (roomTypeRepo.existsById(roomTypeId))
        {
            roomTypeRepo.deleteById(roomTypeId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<RoomTypeDTO> getALlRoomType(int hotelId){
        List<RoomType> roomTypeList=roomTypeRepo.findByHotel_HotelId(hotelId);
        return modelMapper.map(roomTypeList,new TypeToken<ArrayList<RoomTypeDTO>>(){
        }.getType());
    }



}
