package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Service.HotelService;
import com.travelPlus.v1.Utill.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ResponseDTO responseDTO;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity addHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            long res = hotelService.addHotel(hotelDTO);
            if (res>0) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(res);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res==0) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Already Added");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }
        catch( Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity updateHotel(@RequestBody HotelDTO hotelDTO){

        try{
            String res= hotelService.updateHotel(hotelDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_SUCCESS );
                responseDTO.setMessage("Success");
                responseDTO.setContent(hotelDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("001")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND );
                responseDTO.setMessage("Not a Registered Hotel ");
                responseDTO.setContent(hotelDTO);
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

    @GetMapping
    public ResponseEntity getAllHotel(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageRequest  = PageRequest.of(page, size);
            Page<Hotel> hotelPage = hotelService.getAllHotel(pageRequest );
            List<HotelDTO> hotelDTOs = hotelPage.getContent().stream()
                    .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                    .collect(Collectors.toList());

            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(hotelDTOs);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        } catch(Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchHotel(@RequestParam String destination,
                                      @RequestParam String checkIn,
                                      @RequestParam String checkOut,
                                      @RequestParam int guestCount,
                                      @RequestParam int numberOfRooms,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);

            List<HotelDTO> hotels = hotelService.searchHotel(destination, checkIn, checkOut, guestCount, numberOfRooms, pageRequest);

            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(hotels);

            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity getHotel(@PathVariable long hotelId){
        try{

            HotelDTO hotelDTO=hotelService.getHotel(hotelId);
            if(hotelDTO!=null){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(hotelDTO);
                return  new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
            }
            else{
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage(" User not found!");
                responseDTO.setContent(hotelDTO);
                return  new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
            }

        }
        catch (Exception ex){
            responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent((null));
            return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity deleteHotel(@PathVariable int hotelId){
        try{
            String emp= hotelService.deleteHotel(hotelId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Hotel found ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }}catch( Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

