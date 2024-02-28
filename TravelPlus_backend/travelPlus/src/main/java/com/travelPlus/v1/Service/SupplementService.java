package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.SupplementDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Supplement;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.RoomTypeRepo;
import com.travelPlus.v1.Repo.SupplementRepo;
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
public class SupplementService {
    @Autowired
    private SupplementRepo supplementRepo;
    @Autowired
    private ContractRepo contractRepo;

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
            supplementRepo.save(modelMapper.map(supplementDTO, Supplement.class));
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

    public String deleteSupplement(int suppelemtId) {
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
