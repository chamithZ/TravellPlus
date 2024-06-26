package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.DTO.SupplementDTO;
import com.travelPlus.v1.Service.RoomTypeService;
import com.travelPlus.v1.Service.SupplementService;
import com.travelPlus.v1.Utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/supplements")
public class SupplementController {
    @Autowired
    private SupplementService supplementService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping
    public ResponseEntity addSupplements(@RequestBody List<SupplementDTO> supplementDTOList) {
        try {
            List<String> responses = new ArrayList<>();

            for (SupplementDTO supplementDTO : supplementDTOList) {
                String res = supplementService.addSupplement(supplementDTO);
                responses.add(res);
            }

            List<SupplementDTO> successfulSupplements = new ArrayList<>();
            List<SupplementDTO> duplicateSupplements = new ArrayList<>();
            List<SupplementDTO> unavailableContracts = new ArrayList<>();

            for (int i = 0; i < responses.size(); i++) {
                String res = responses.get(i);
                SupplementDTO supplementDTO = supplementDTOList.get(i);

                if (res.equals("000")) {
                    successfulSupplements.add(supplementDTO);
                } else if (res.equals("006")) {
                    duplicateSupplements.add(supplementDTO);
                } else if (res.equals("011")) {
                    unavailableContracts.add(supplementDTO);
                }
            }

            if (!successfulSupplements.isEmpty()) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(successfulSupplements);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            } else if (!duplicateSupplements.isEmpty()) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Some supplements were already added");
                responseDTO.setContent(duplicateSupplements);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else if (!unavailableContracts.isEmpty()) {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Some contracts are not available");
                responseDTO.setContent(unavailableContracts);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity updateRoomType(@RequestBody SupplementDTO supplementDTO){

        try{
            String res= supplementService.updateSupplement(supplementDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(supplementDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("001")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND );
                responseDTO.setMessage("Room type is not available ");
                responseDTO.setContent(supplementDTO);
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

    @DeleteMapping
    public ResponseEntity deleteHotel(@PathVariable int supplementId){
        try{
            String emp= supplementService.deleteSupplement(supplementId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Supplement is not available ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }}catch( Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{contractId}")
    public ResponseEntity getAllSupplements(@PathVariable int contractId){
        try{
            List<SupplementDTO> emp=supplementService.getAllSupplements(contractId);
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
