package com.travelPlus.UnitTests;

import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Service.UserService;
import com.travelPlus.v1.Utill.VarList;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testSaveUser() {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setUserStatus(true);

        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setUserStatus(true);

        // Mock behavior
        when(userRepo.save(any())).thenReturn(user);

        // Call the service method
        User savedUser = userService.save(user);

        // Assert the result
        assertNotNull(savedUser);
        assertEquals(userDTO.getName(), savedUser.getName());
        assertEquals(userDTO.getEmail(), savedUser.getEmail());
        assertEquals(userDTO.isUserStatus(), savedUser.isUserStatus());
    }

    @Test
    @Order(2)
    public void testUpdateUser() {
        // Mock data
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setName("Updated Name");

        User user = new User();
        user.setUserId(1L);
        user.setName("Original Name");

        // Mock behavior
        when(userRepo.existsById(anyLong())).thenReturn(true);
        when(userRepo.save(any())).thenReturn(user);

        // Call the service method
        String result = userService.updateUser(userDTO);

        // Assert the result
        assertEquals("000", result);
    }

    @Test
    @Order(6)
    public void testDeleteUser() {
        // Mock data
        long userId = 1L;

        // Mock behavior
        when(userRepo.existsById(userId)).thenReturn(true);
        when(userRepo.findById(userId)).thenReturn(Optional.of(new User()));

        // Call the service method
        String result = userService.deleteUser(userId);

        // Assert the result
        assertEquals(VarList.RSP_SUCCESS, result);
    }

    @Test
    @Order(4)
    public void testGetAllUsers() {
        // Mock data
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        // Mock behavior
        when(userRepo.findAll()).thenReturn(userList);
        when(modelMapper.map(userList, new TypeToken<ArrayList<UserDTO>>(){}.getType())).thenReturn(new ArrayList<>());

        // Call the service method
        List<UserDTO> result = userService.getAllUsers();

        // Assert the result
        assertNotNull(result);
    }

    @Test
    @Order(5)
    public void testGetUserByEmail() {
        // Mock data
        String email = "john@example.com";
        User user = new User();
        user.setEmail(email);

        // Mock behavior
        when(userRepo.existsUserByEmail(email)).thenReturn(true);
        when(userRepo.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(new UserDTO());

        // Call the service method
        UserDTO result = userService.getUser(email);

        // Assert the result
        assertNotNull(result);
    }

    @Test
    @Order(3)
    public void testGetUsersWithPropertyIdGreaterThanZero() {
        // Mock data
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        // Mock behavior
        when(userRepo.findByPropertyIdGreaterThanAndUserStatusIsTrue(anyLong())).thenReturn(userList);
        when(modelMapper.map(userList, new TypeToken<ArrayList<UserDTO>>(){}.getType())).thenReturn(new ArrayList<>());

        // Call the service method
        List<UserDTO> result = userService.getUsersWithPropertyIdGreaterThanZero();

        // Assert the result
        assertNotNull(result);
    }
}
