package com.revature.taskmaster.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
    boolean existsByEmailAddress(String emailAddress);
    Optional<User> findUserByEmailAddress(String emailAddress);
}
