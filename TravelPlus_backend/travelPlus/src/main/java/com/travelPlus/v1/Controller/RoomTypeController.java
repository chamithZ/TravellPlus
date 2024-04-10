package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.DTO.RoomTypeDTO;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Service.RoomTypeService;
import com.travelPlus.v1.Utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roomTypes")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping
    public ResponseEntity addRoomTypes(@RequestBody List<RoomTypeDTO> roomTypeDTOs) {
        try {
            List<RoomTypeDTO> addedRoomTypes = new ArrayList<>();

            for (RoomTypeDTO roomTypeDTO : roomTypeDTOs) {
                String res = roomTypeService.addRoomType(roomTypeDTO);

                if (res.equals("000")) {
                    addedRoomTypes.add(roomTypeDTO);
                }
            }

            if (!addedRoomTypes.isEmpty()) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(addedRoomTypes);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("All room types already added or not available");
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
    public ResponseEntity updateRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {

        try {
            String res = roomTypeService.updateRoomType(roomTypeDTO);
            if (res.equals("000")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Success");
                responseDTO.setContent(roomTypeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            } else if (res.equals("001")) {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Room type is not available ");
                responseDTO.setContent(roomTypeDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            } else {
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

    @GetMapping("/{contractId}/{checkInDate}/{checkOutDate}")
    public ResponseEntity<List<RoomType>> findAvailableRoomTypes(
            @PathVariable long contractId,
            @PathVariable("checkInDate") String checkInDateStr,
            @PathVariable("checkOutDate") String checkOutDateStr
    ) {
        try {
            LocalDate checkInDate = LocalDate.parse(checkInDateStr);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

            List<RoomType> availableRoomTypes = roomTypeService.findAvailableRoomTypes(contractId, checkInDate, checkOutDate);

            return new ResponseEntity<>(availableRoomTypes, HttpStatus.OK);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/availableRoomCount")
    public List<Object[]> getAvailableRoomCount(
            @RequestParam int contractId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        return roomTypeService.getAvailableRoomCountByContractId(contractId, checkInDate, checkOutDate);
    }



    @DeleteMapping("/{roomTypeId}")
    public ResponseEntity deleteHotel(@PathVariable int roomTypeId){
        try{
            String emp= roomTypeService.deleteRoomType(roomTypeId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Room Type is not available ");
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
    public ResponseEntity getAllRoomType(@PathVariable long contractId){
        try{
            List<RoomType> emp=roomTypeService.getALlRoomType(contractId);
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
