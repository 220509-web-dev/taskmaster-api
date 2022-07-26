package com.revature.taskmaster.test_utils;

import com.revature.taskmaster.user.User;
import com.revature.taskmaster.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MockUserFactory {

    private final UserRepository userRepo;

    @Autowired
    public MockUserFactory(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllMockUsers() {
        return this.userRepo.findAll();
    }

    public User getMockUserByRole(User.Role role) {
        switch (role) {
            case ADMIN:
                return userRepo.findById("admin-user-id").orElse(null);
            case MANAGER:
                return userRepo.findById("manager-user-id").orElse(null);
            case DEV:
                return userRepo.findById("dev-user-id").orElse(null);
            case TESTER:
                return userRepo.findById("tester-user-id").orElse(null);
            case LOCKED:
                return userRepo.findById("locked-user-id").orElse(null);
        }
        return null;
    }

    public User getInactiveUser() {
        return userRepo.findById("inactive-user-id").orElse(null);
    }

}
