package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String updateEmployee(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId())){
            userRepo.save(modelMapper.map(userDTO,User.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteUser(int userId) {
        if (userRepo.existsById(userId))
        {
            userRepo.deleteById(userId);
            return VarList.RSP_SUCCESS;
        }
         else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
