package com.travelPlus.v1.Service;


import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.RoomTypeSeasonDTO;
import com.travelPlus.v1.DTO.SeasonDTO;
import com.travelPlus.v1.Entity.*;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomTypeService {
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private SeasonRepo seasonRepo;
    @Autowired
    private RoomTypeSeasonRepo roomTypeSeasonRepo;
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private SeasonService seasonService;

    @Autowired
    private ModelMapper modelMapper;


    public String addRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();

        roomType.setRoomTypeName(roomTypeDTO.getRoomTypeName());
        roomType.setRoomSize(roomTypeDTO.getRoomSize());
        roomType.setRoomBedType(roomTypeDTO.getRoomBedType());
        roomType.setRoomDescription(roomTypeDTO.getRoomDescription());
        roomType.setRoomImage(roomTypeDTO.getRoomImage());

        Contract contract = new Contract();
        contract.setContractId(roomTypeDTO.getContractId());
        roomType.setContract(contract);

        // Iterate over the list of SeasonRoomTypeDTO
        for (RoomTypeSeasonDTO seasonRoomTypeDTO : roomTypeDTO.getRoomTypeSeasons()) {
            Season season = seasonRepo.findById(seasonRoomTypeDTO.getSeason().getSeasonId())
                    .orElseThrow(() -> new RuntimeException("Season not found"));

            // Create a new RoomTypeSeason
            RoomTypeSeason roomTypeSeason = new RoomTypeSeason();
            roomTypeSeason.setId(new RoomTypeSeasonKey(roomType.getRoomId(), season.getSeasonId()));
            roomTypeSeason.setRoomType(roomType);
            roomTypeSeason.setSeason(season);
            roomTypeSeason.setRoomPrice(seasonRoomTypeDTO.getRoomPrice());
            roomTypeSeason.setNoOfRooms(seasonRoomTypeDTO.getNoOfRooms());

            // Add the RoomTypeSeason to the RoomType's collection
            roomType.getRoomTypeSeasons().add(roomTypeSeason);
        }

        // Save the RoomType along with its associated RoomTypeSeasons
        roomTypeRepo.save(roomType);
        return VarList.RSP_SUCCESS;
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

    public String deleteRoomType(long roomTypeId) {
        if (roomTypeRepo.existsById(roomTypeId))
        {
            roomTypeRepo.deleteById(roomTypeId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<RoomType> getALlRoomType(long contractId){
        List<RoomType> roomTypeList=roomTypeRepo.findByContract_contractId(contractId);
        return roomTypeList;
    }




}
