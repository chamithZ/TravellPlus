package com.travelPlus.v1.Config;

import com.travelPlus.v1.Entity.User;
import com.travelPlus.v1.Repo.UserRepo;
import com.travelPlus.v1.Service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final AuthService userService;
    private final PasswordEncoder passwordEncoder;
    private final Lock lock = new ReentrantLock();

    public AdminUserInitializer(UserRepo userRepository, AuthService userService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Runnable initializationTask = () -> {
            // Execute database operation within the separate thread
            if (!userRepository.existsUserByUserType("admin")) {
                lock.lock(); // Add thread safety
                try {
                    if (!userRepository.existsUserByUserType("admin")) { // Double check inside the lock
                        // Create admin user if it doesn't exist
                        User adminUser = User.builder()
                                .name("Admin")
                                .userType("admin")
                                .email("admin@t.com")
                                .password(passwordEncoder.encode("16820"))
                                .build();
                        userRepository.save(adminUser);
                    }
                } finally {
                    lock.unlock(); // Release the lock
                }
            }

        };

        Thread thread = new Thread(initializationTask);
        thread.start();
    }


}
