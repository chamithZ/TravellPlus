package com.travelPlus.v1.Controller;

import com.travelPlus.v1.DTO.AuthDTO;
import com.travelPlus.v1.DTO.JWTResponse;
import com.travelPlus.v1.DTO.ResponseDTO;
import com.travelPlus.v1.DTO.UserDTO;
import com.travelPlus.v1.Service.UserService;
import com.travelPlus.v1.Utill.VarList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelPlus.v1.Service.UserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ResponseDTO responseDTO;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @PostMapping("/register")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO){
        try{
            String res= userService.saveUser(userDTO);
            if(res.equals("000")){
                responseDTO.setCode(VarList.RSP_SUCCESS );
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else if(res.equals("006")){
                responseDTO.setCode(VarList.RSP_DUPLICATED );
                responseDTO.setMessage("Already registered");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else{
                responseDTO.setCode(VarList.RSP_FAIL );
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }

        }
        catch(Exception e){
            responseDTO.setCode(VarList.RSP_ERROR );
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        if (userDetails != null && passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = generateJwtToken(userDetails);
            return ResponseEntity.ok(new JWTResponse(jwtToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logout successful");
    }
    private String generateJwtToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String secret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return token;
    }
}
