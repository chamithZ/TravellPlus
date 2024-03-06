package com.travelPlus.v1;

import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Service.UserService;
import com.travelPlus.v1.Utill.VarList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveUser() {
        // Mock data
        UserDTO userDTO = createSampleUserDTO();

        // Mock repository behavior
        when(userRepo.existsById(userDTO.getUserId())).thenReturn(false);
        when(userRepo.existsUserByEmail(userDTO.getEmail())).thenReturn(false);
        when(userRepo.existsUserByNic(userDTO.getNic())).thenReturn(false);
        when(userRepo.save(any(User.class))).thenReturn(new User());

        // Call the service method
        String result = userService.saveUser(userDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testSaveUserDuplicate() {
        // Mock data
        UserDTO userDTO = createSampleUserDTO();

        // Mock repository behavior for duplicate user
        when(userRepo.existsById(userDTO.getUserId())).thenReturn(true);

        // Call the service method
        String result = userService.saveUser(userDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_DUPLICATED);
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        // Mock data
        UserDTO userDTO = createSampleUserDTO();

        // Mock repository behavior
        when(userRepo.existsById(userDTO.getUserId())).thenReturn(true);
        when(userRepo.save(any(User.class))).thenReturn(new User());

        // Call the service method
        String result = userService.updateUser(userDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_SUCCESS);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserNotFound() {
        // Mock data
        UserDTO userDTO = createSampleUserDTO();

        // Mock repository behavior for user not found
        when(userRepo.existsById(userDTO.getUserId())).thenReturn(false);

        // Call the service method
        String result = userService.updateUser(userDTO);

        // Assertions
        assertThat(result).isEqualTo(VarList.RSP_NO_DATA_FOUND);
        verify(userRepo, never()).save(any(User.class));
    }

    // ... (Other test cases remain the same)

    private UserDTO createSampleUserDTO() {
        // Create a sample UserDTO for testing
        UserDTO userDTO = new UserDTO();
        // Set other fields as needed
        // ...

        return userDTO;
    }
}
