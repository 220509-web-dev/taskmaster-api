package com.revature.taskmaster.repos;

import com.revature.taskmaster.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmailAddress(String emailAddress);
    User findUserByUsernameAndPassword(String username, String password);
}
