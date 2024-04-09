package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.Service.ReservationRoomTypeService;
import com.travelPlus.v1.Utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservationRooms")
public class ReservationRoomTypeController {
    @Autowired
    ReservationRoomTypeService reservationRoomType;
    @Autowired
    ResponseDTO responseDTO;
    @GetMapping("/{reservationId}")
    public ResponseEntity getAllRooms(@PathVariable long reservationId){
        try{
            List<com.travelPlus.v1.Entity.ReservationRoomType> rooms=reservationRoomType.getAllReservedRooms(reservationId);
            responseDTO.setCode(VarList.RSP_DUPLICATED );
            responseDTO.setMessage("Success");
            responseDTO.setContent(rooms);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

        }catch(Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent((null));
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
