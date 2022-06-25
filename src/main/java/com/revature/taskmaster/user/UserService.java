package com.revature.taskmaster.user;

import com.revature.taskmaster.auth.dtos.AuthRequest;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.user.dtos.NewUserRequest;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.UpdateUserRequest;
import com.revature.taskmaster.user.dtos.UserResponse;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.common.util.exceptions.ResourcePersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional // implies @Transactional on all methods in this class
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResponse> fetchAllUsers() {
        return userRepo.findAll()
                       .stream()
                       .map(UserResponse::new)
                       .collect(Collectors.toList());
    }

    public List<UserResponse> fetchUsersByRole(String role) {
        return userRepo.findUsersByRole(role)
                       .stream()
                       .map(UserResponse::new).
                       collect(Collectors.toList());
    }

    public UserResponse fetchUserById(String id) {
        return userRepo.findById(id)
                       .map(UserResponse::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    public UserResponse fetchUserByUsername(@Min(3) String username) {
        return userRepo.findUserByUsername(username)
                       .map(UserResponse::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    public UserResponse fetchUserByEmail(@Email String email) {
        return userRepo.findUserByEmailAddress(email)
                       .map(UserResponse::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    public ResourceCreationResponse createUser(@Valid NewUserRequest newUserRequest) {

        User newUser = newUserRequest.extractResource();

        if (userRepo.existsByUsername(newUser.getUsername())) {
            throw new ResourcePersistenceException("There is already a user with that username!");
        }

        if (userRepo.existsByEmailAddress(newUser.getEmailAddress())) {
            throw new ResourcePersistenceException("There is already a user with that email!");
        }

        newUser.setId(UUID.randomUUID().toString());
        newUser.setRole(User.Role.LOCKED);
        userRepo.save(newUser);

        return new ResourceCreationResponse(newUser.getId());

    }

    public void updateUser(@Valid UpdateUserRequest updateUserRequest) {
        // TODO implement update
    }

    public UserResponse authenticateUserCredentials(@Valid AuthRequest authRequest) {
        return userRepo.findUserByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword())
                       .map(UserResponse::new)
                       .orElseThrow(AuthenticationException::new);
    }

}
