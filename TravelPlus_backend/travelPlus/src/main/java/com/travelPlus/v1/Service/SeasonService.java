package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.SeasonDTO;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Season;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.RoomTypeRepo;
import com.travelPlus.v1.Repo.SeasonRepo;
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
public class SeasonService {
    @Autowired
    private SeasonRepo seasonRepo;
    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addSeason(SeasonDTO seasonDTO){
        if(seasonRepo.existsById(seasonDTO.getSeasonId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!contractRepo.existsById(seasonDTO.getContractId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            seasonRepo.save(modelMapper.map(seasonDTO, Season.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateSeason (SeasonDTO seasonDTO){
        if(seasonRepo.existsById(seasonDTO.getSeasonId())){
            seasonRepo.save(modelMapper.map(seasonDTO,Season.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteSeason(int seasonId) {
        if (seasonRepo.existsById(seasonId))
        {
            seasonRepo.deleteById(seasonId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<SeasonDTO> getAllSeason(int contractId){
        List<Season> roomTypeList=seasonRepo.findByContract_contractId(contractId);
        return modelMapper.map(roomTypeList,new TypeToken<ArrayList<RoomTypeDTO>>(){
        }.getType());
    }

}
