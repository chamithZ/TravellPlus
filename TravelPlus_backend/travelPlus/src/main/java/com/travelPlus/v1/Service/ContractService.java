package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Repo.ContractRepo;
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
public class ContractService {

    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private ModelMapper modelMapper;

    public String addContract(ContractDTO contractDTO){
        if(contractRepo.existsById(contractDTO.getContractId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!hotelRepo.existsById(contractDTO.getHotelId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            contractRepo.save(modelMapper.map(contractDTO, Contract.class));
            return VarList.RSP_SUCCESS;
        }
    }
    public String updateContract(ContractDTO contractDTO){
        if(contractRepo.existsById(contractDTO.getHotelId())){
            contractRepo.save(modelMapper.map(contractDTO,Contract.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
    public String deleteContract(int contractId) {
        if (contractRepo.existsById(contractId))
        {
            contractRepo.deleteById(contractId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<ContractDTO> getAllContracts(){
        List<Contract> contractList=contractRepo.findAll();
        return modelMapper.map(contractList,new TypeToken<ArrayList<ContractDTO>>(){
        }.getType());
    }



}
