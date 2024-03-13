package com.travelPlus.v1.Service;

import com.travelPlus.v1.Config.PasswordConfig;
import com.travelPlus.v1.DTO.ContractDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.Contract;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordConfig passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    public String saveUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId()) || userRepo.existsUserByEmail(userDTO.getEmail()) || userRepo.existsUserByNic(userDTO.getNic())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            userDTO.setPassword(passwordEncoder.passwordEncoder().encode(userDTO.getPassword()));
            userRepo.save(modelMapper.map(userDTO, User.class));

            return VarList.RSP_SUCCESS;
        }
    }
    public User save(User newUser) {
        if (newUser.getUserId() == 0) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepo.save(newUser);
    }


    public String updateUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId())){
            userRepo.save(modelMapper.map(userDTO,User.class));
            return VarList.RSP_SUCCESS;
        }
        else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public String deleteUser(long userId) {
        if (userRepo.existsById(userId))
        {
            userRepo.deleteById(userId);
            return VarList.RSP_SUCCESS;
        }
         else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
    public List<UserDTO> getAllUsers(){
        List<User> userList=userRepo.findAll();
        return modelMapper.map(userList,new TypeToken<ArrayList<UserDTO>>(){
        }.getType());
    }

    public UserDTO getUser(long userId) {
        if(userRepo.existsById(userId)){
            User user=userRepo.findById(userId).orElse(null);
            return modelMapper.map(user,UserDTO.class);
        }
        else{
            return null;
        }
    }
}
