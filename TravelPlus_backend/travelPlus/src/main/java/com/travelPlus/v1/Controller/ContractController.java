package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.DTO.HotelDTO;
import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.Hotel;
import com.travelPlus.v1.Entity.RoomType;
import com.travelPlus.v1.Service.ContractService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/contracts")

public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private ResponseDTO responseDTO;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity addContract(@RequestBody ContractDTO contractDTO) {
        try {
            long res = contractService.addContract(contractDTO);
            if (res==-1) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Already Added");
                responseDTO.setContent(contractDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else if (res==-2) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Hotel not available");
                responseDTO.setContent(contractDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else {
                contractDTO.setContractId(res);
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(contractDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
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
    public ResponseEntity updateContract(@RequestBody ContractDTO contractDTO){
    System.out.println("menna");
        try{
            String res= contractService.updateContract(contractDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_SUCCESS );
                responseDTO.setMessage("Success");
                responseDTO.setContent(contractDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("001")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND );
                responseDTO.setMessage("Contract is not available ");
                responseDTO.setContent(contractDTO);
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
    public ResponseEntity getAllContracts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size, @RequestParam String requestType) {
        try {
            Pageable pageRequest  = PageRequest.of(page, size);

            if(requestType.equals("active")){
                Page<Contract> contractPage = contractService.getAllContracts(pageRequest );
                List<ContractDTO> contractDTOs = contractPage.getContent().stream()
                        .map(contract -> modelMapper.map(contract, ContractDTO.class))
                        .collect(Collectors.toList());
                responseDTO.setContent(contractDTOs);

            }
            else{
                Page<Contract> contractPage = contractService.getAllDisabledContracts(pageRequest );
                List<ContractDTO> contractDTOs = contractPage.getContent().stream()
                        .map(contract -> modelMapper.map(contract, ContractDTO.class))
                        .collect(Collectors.toList());
                responseDTO.setContent(contractDTOs);

            }
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");

            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        } catch(Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PatchMapping ("/{contractId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity enableContract(@PathVariable int contractId){
        try{
            String res= contractService.enableContract(contractId);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_SUCCESS );
                responseDTO.setMessage("Success");
                responseDTO.setContent(res);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Contract found ");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }}catch( Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/{contractId}")
    public ResponseEntity deleteContract(@PathVariable int contractId){
        try{
            String emp= contractService.deleteContract(contractId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Contract found ");
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
    public ResponseEntity<ResponseDTO> getContractIdByHotelIdAndDateRange(
            @PathVariable long hotelId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        try {
            LocalDate parsedStartDate = LocalDate.parse(startDate);
            LocalDate parsedEndDate = LocalDate.parse(endDate);

            long contractId = contractService.getContractIdByHotelIdAndDateRange(hotelId, parsedStartDate, parsedEndDate);

            if (contractId != -1) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Contract ID found");
                responseDTO.setContent(contractId);
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No contract found for the given hotel ID and date range");
                responseDTO.setContent(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }
    @GetMapping("getContract/{contractId}")
    public ResponseEntity<ResponseDTO> getContractById(@PathVariable long contractId) {
        try {

            ContractDTO contractDTO = contractService.getContractById(contractId);

            if (contractDTO !=null) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Contract found");
                responseDTO.setContent(contractDTO);
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No contract found for contractId");
                responseDTO.setContent(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }



}
