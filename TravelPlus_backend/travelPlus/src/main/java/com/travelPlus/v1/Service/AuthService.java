package com.travelPlus.v1.Service;

import com.travelPlus.v1.DTO.AuthDTO;
import com.travelPlus.v1.DTO.JWTResponse;
import com.travelPlus.v1.DTO.LoginDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public JWTResponse signup(UserDTO request) {
        var user = User
                .builder()
                .name(request.getName())
                .userType(request.getUserType())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .contactNo(request.getContactNo())
                .nic(request.getNic())
                .age(request.getAge())
                .userType(request.getUserType())
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JWTResponse.builder().token(jwt).build();
    }

    public JWTResponse signin(AuthDTO request) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid email or password."+ex);
        }

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        var jwt = jwtService.generateToken(user);
        return JWTResponse.builder().token(jwt).build();
    }
}
