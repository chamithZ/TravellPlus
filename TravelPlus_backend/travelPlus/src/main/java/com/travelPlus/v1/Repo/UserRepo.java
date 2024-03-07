package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);

    boolean existsUserByNic(String nic);
}
