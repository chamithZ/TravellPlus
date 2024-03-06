package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.OfferDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.SupplementDTO;
import com.travelPlus.v1.Entity.Offer;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Entity.Supplement;
import com.travelPlus.v1.Repo.ContractRepo;
import com.travelPlus.v1.Repo.HotelRepo;
import com.travelPlus.v1.Repo.OfferRepo;
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
public class OfferService {
    @Autowired
    private OfferRepo offerRepo;
    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String addOffer(OfferDTO offerDTO){
        if(offerRepo.existsById(offerDTO.getOfferId())){
            return VarList.RSP_DUPLICATED;
        }
        else if(!contractRepo.existsById(offerDTO.getContractId())){
            return VarList.RSP_NotAvailable;
        }
        else{
            offerRepo.save(modelMapper.map(offerDTO, Offer.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateOffer(OfferDTO offerDTO){
        if(offerRepo.existsById(offerDTO.getOfferId())){
            offerRepo.save(modelMapper.map(offerDTO,Offer.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteOffer(long offerId) {
        if (offerRepo.existsById(offerId))
        {
            offerRepo.deleteById(offerId);
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<OfferDTO> getAllOffers(int contractId){
        List<Offer> OfferList=offerRepo.findByContract_contractId(contractId);
        return modelMapper.map(OfferList,new TypeToken<ArrayList<OfferDTO>>(){
        }.getType());
    }


}
