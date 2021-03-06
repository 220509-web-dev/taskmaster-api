package com.revature.taskmaster.auth;

import com.revature.taskmaster.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
