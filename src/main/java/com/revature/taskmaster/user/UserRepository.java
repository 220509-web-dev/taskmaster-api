package com.revature.taskmaster.user;

import com.revature.taskmaster.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
    boolean existsByEmailAddress(String emailAddress);

    @Query("from User u where u.role = :role")
    List<User> findUsersByRole(String role);

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmailAddress(String emailAddress);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
