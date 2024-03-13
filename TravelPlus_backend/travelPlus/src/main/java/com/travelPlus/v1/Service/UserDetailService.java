package com.travelPlus.v1.Service;

import com.travelPlus.v1.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserRepo userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return (UserDetails) userRepository.findUserByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            }
        };
    }
}
