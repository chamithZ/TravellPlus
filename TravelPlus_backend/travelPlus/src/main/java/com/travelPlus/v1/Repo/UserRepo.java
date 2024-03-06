package com.travelPlus.v1.Repo;

import com.travelPlus.v1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsUserByEmail(String email);
    User findUserByEmail(String email);
    boolean existsUserByNic(String nic);
}
