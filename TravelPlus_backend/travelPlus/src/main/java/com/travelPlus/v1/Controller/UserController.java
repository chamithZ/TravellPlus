package com.travelPlus.v1.Controller;


import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Service.UserService;
import com.travelPlus.v1.Utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResponseDTO responseDTO;



    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        try{
            String res= userService.updateUser(userDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_SUCCESS );
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);

            }else if(res.equals("001")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND );
                responseDTO.setMessage("Not a Registered user ");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL );
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);

            }
        }catch( Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable long userId){
        try{
            String emp= userService.deleteUser(userId);
            if(emp.equals("000")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Success");
                responseDTO.setContent(emp);
                return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
            }
            else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No User found ");
                responseDTO.setContent(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);

            }}catch( Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllUsers(){
        try{
            List<UserDTO> emp=userService.getAllUsers();
            responseDTO.setCode(VarList.RSP_SUCCESS );
            responseDTO.setMessage("Success");
            responseDTO.setContent(emp);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);

        }catch(Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent((null));
            return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admins")
    public ResponseEntity getAllAdmins(){
        try{
            List<UserDTO> userList=userService.getUsersWithPropertyIdGreaterThanZero();
            responseDTO.setCode(VarList.RSP_SUCCESS );
            responseDTO.setMessage("Success");
            responseDTO.setContent(userList);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);

        }catch(Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent((null));
            return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{email}")
    public ResponseEntity getUser(@PathVariable String email){
        try{

            UserDTO userDTO=userService.getUser(email);
            if(userDTO!=null){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return  new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
            }
            else{
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage(" User not found!");
                responseDTO.setContent(userDTO);
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
    @GetMapping("/userId/{userId}")
    public ResponseEntity getUserByUserId(@PathVariable long userId){
        try{

            UserDTO userDTO=userService.getUserByUserId(userId);
            if(userDTO!=null){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return  new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
            }
            else{
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage(" User not found!");
                responseDTO.setContent(userDTO);
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

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(new ResponseDTO("011","error", response));
    }

}
