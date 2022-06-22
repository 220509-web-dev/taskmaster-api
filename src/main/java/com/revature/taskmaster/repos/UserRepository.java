package com.revature.taskmaster.repos;

import com.revature.taskmaster.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
    boolean existsByEmailAddress(String emailAddress);

    User findUserByUsername(String username);
    User findUserByEmailAddress(String emailAddress);
    User findUserByUsernameAndPassword(String username, String password);
}
