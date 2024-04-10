package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.OfferDTO;
import com.travelPlus.v1.DTO.SeasonDTO;
import com.travelPlus.v1.Entity.*;
import com.travelPlus.v1.Repo.*;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContractService {

    @Autowired
    private ContractRepo contractRepo;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private SeasonRepo seasonRepo;
    @Autowired
    private SupplementRepo supplementRepo;
    @Autowired
    private OfferRepo offerRepo;
    @Autowired
    private RoomTypeRepo roomTypeRepo;
    @Autowired
    private ModelMapper modelMapper;

    public long addContract(ContractDTO contractDTO) {
        if (contractRepo.existsById(contractDTO.getContractId())) {
            return -1;
        } else if (!hotelRepo.existsById(contractDTO.getHotelId())) {
            return -2;
        } else {
            // Save the Contract entity
            Contract contract = modelMapper.map(contractDTO, Contract.class);
            contractRepo.save(contract);

            // Retrieve the generated contractId
            long contractId = contract.getContractId();

            // Save the Season entities with the contractId
            for (SeasonDTO seasonDTO : contractDTO.getSeason()) {
                seasonDTO.setContractId(contractId);
                Season season = modelMapper.map(seasonDTO, Season.class);
                seasonRepo.save(season);
            }
            for (OfferDTO offerDTO : contractDTO.getOffer()) {
                offerDTO.setContractId(contractId);
                Offer offer = modelMapper.map(offerDTO, Offer.class);
                offerRepo.save(offer);
            }

            return contractId;
        }
    }
    public String updateContract(ContractDTO contractDTO){
        if(contractRepo.existsById(contractDTO.getContractId())){
            contractRepo.save(modelMapper.map(contractDTO,Contract.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
    public String deleteContract(long contractId) {
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
    public long getContractIdByHotelIdAndDateRange(long hotelId, LocalDate startDate, LocalDate endDate) {
        Contract contract = contractRepo.findByHotelHotelIdAndStartDateBeforeAndEndDateAfter(hotelId, startDate, endDate);
        if (contract != null) {
            return contract.getContractId();
        } else {
            // No contract found for the given hotel ID and date range
            return -1; // Or you can throw an exception or return null depending on your application's logic
        }
    }
    public ContractDTO getContractById(long contractId) {
       Contract contract = contractRepo.findById(contractId).orElse(null);
        System.out.println(contractId);
        if (contract!=null) {
            return modelMapper.map(contract,ContractDTO.class);
        } else {
            return null; // Or you can throw an exception or return null depending on your application's logic
        }
    }
    



}
