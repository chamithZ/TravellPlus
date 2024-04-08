package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.RoomTypeSeasonDTO;
import com.travelPlus.v1.DTO.SupplementDTO;
import com.travelPlus.v1.DTO.SupplementSeasonDTO;
import com.travelPlus.v1.Entity.*;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplementService {
    @Autowired
    private SupplementRepo supplementRepo;
    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private SeasonRepo seasonRepo;
    @Autowired
    private SeasonService seasonService;

    @Autowired
    private ModelMapper modelMapper;

    public String addSupplement(SupplementDTO  supplementDTO){
        if(supplementRepo.existsById(supplementDTO.getSupplementId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!contractRepo.existsById(supplementDTO.getContractId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            Supplement supplement = new Supplement();
            supplement.setServiceName(supplementDTO.getServiceName());

            Contract contract = new Contract();
            contract.setContractId(supplementDTO.getContractId());
            supplement.setContract(contract);

            // Iterate over the list of SeasonRoomTypeDTO
            for (SupplementSeasonDTO supplementSeasonDTO : supplementDTO.getSupplementSeason()) {
                Season season = seasonRepo.findById(supplementSeasonDTO.getSeason().getSeasonId())
                        .orElseThrow(() -> new RuntimeException("Season not found"));


                SupplementSeason supplementSeason = new SupplementSeason();
                supplementSeason.setId(new SupplementSeasonKey(supplement.getSupplementId(), season.getSeasonId()));
                supplementSeason.setSupplement(supplement);
                supplementSeason.setSeason(season);
                supplementSeason.setPrice(supplementSeasonDTO.getPrice());


                supplement.getSupplementSeason().add(supplementSeason);
            }

            // Save the RoomType along with its associated RoomTypeSeasons
            supplementRepo.save(supplement);
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateSupplement(SupplementDTO supplementDTO){
        if(supplementRepo.existsById(supplementDTO.getSupplementId())){
            supplementRepo.save(modelMapper.map(supplementDTO,Supplement.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteSupplement(long suppelemtId) {
        if (supplementRepo.existsById(suppelemtId))
        {
            supplementRepo.deleteById(suppelemtId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<SupplementDTO> getAllSupplements(int contractId){
        List<Supplement> SupplementList=supplementRepo.findByContract_contractId(contractId);
        return modelMapper.map(SupplementList,new TypeToken<ArrayList<SupplementDTO>>(){
        }.getType());
    }



}
