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

    private final Map<String, User> mockUsers = new HashMap<>();

    @Autowired
    public MockUserFactory(UserRepository userRepo) {
        this.mockUsers.put(User.Role.ADMIN.name(), userRepo.findById("admin-user-id").orElse(null));
        this.mockUsers.put(User.Role.MANAGER.name(), userRepo.findById("manager-user-id").orElse(null));
        this.mockUsers.put(User.Role.DEV.name(), userRepo.findById("dev-user-id").orElse(null));
        this.mockUsers.put(User.Role.TESTER.name(), userRepo.findById("tester-user-id").orElse(null));
        this.mockUsers.put(User.Role.LOCKED.name(), userRepo.findById("locked-user-id").orElse(null));
        this.mockUsers.put("INACTIVE", userRepo.findById("inactive-user-id").orElse(null));
    }

    public List<User> getAllMockUsers() {
        return new ArrayList<>(mockUsers.values());
    }

    public User getMockUserByRole(User.Role role) {
        return this.mockUsers.get(role.name());
    }

    public User getInactiveUser() {
        return this.mockUsers.get("INACTIVE");
    }

}
