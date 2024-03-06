package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.OfferDTO;
import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.Service.OfferService;
import com.travelPlus.v1.Service.RoomTypeService;
import com.travelPlus.v1.Utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offer")
public class OfferController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping
    public ResponseEntity addOffer(@RequestBody OfferDTO offerDTO) {
        try {
            String res = offerService.addOffer(offerDTO);
            if (res.equals("000")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Success");
                responseDTO.setContent(offerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("006")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Already Added");
                responseDTO.setContent(offerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else if (res.equals("011")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Hotel not available");
                responseDTO.setContent(offerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping
    public ResponseEntity updateRoomType(@RequestBody OfferDTO offerDTO){

        try{
            String res= offerService.updateOffer(offerDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(offerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("001")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND );
                responseDTO.setMessage("Room type is not available ");
                responseDTO.setContent(offerDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL );
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }
        }catch( Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity deleteOffer(@PathVariable int OfferId){
        try{
            String emp= offerService.deleteOffer(OfferId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Offer  is not available ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }}catch( Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity getAllOffers(@PathVariable int hotelId){
        try{
            List<OfferDTO> emp=offerService.getAllOffers(hotelId);
            responseDTO.setCode(VarList.RSP_DUPLICATED );
            responseDTO.setMessage("Success");
            responseDTO.setContent(emp);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        }catch(Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent((null));
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
