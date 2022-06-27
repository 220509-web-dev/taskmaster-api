package com.revature.taskmaster.user;

import com.revature.taskmaster.auth.dtos.AuthRequest;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.common.util.web.validators.groups.OnUpdate;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.EmailRequest;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.common.util.exceptions.ResourcePersistenceException;
import com.revature.taskmaster.user.dtos.UsernameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional // implies @Transactional on all methods in this class
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResponsePayload> fetchAllUsers() {
        return userRepo.findAll()
                       .stream()
                       .map(UserResponsePayload::new)
                       .collect(Collectors.toList());
    }

    public List<UserResponsePayload> fetchUsersByRole(String role) {
        return userRepo.findUsersByRole(role)
                       .stream()
                       .map(UserResponsePayload::new).
                       collect(Collectors.toList());
    }

    public UserResponsePayload fetchUserById(String id) {
        return userRepo.findById(id)
                       .map(UserResponsePayload::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    public UserResponsePayload fetchUserByUsername(@Valid UsernameRequest request) {
        return userRepo.findUserByUsername(request.getUsername())
                       .map(UserResponsePayload::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    public boolean checkUsernameAvailability(@Valid UsernameRequest request) {
        return userRepo.existsByUsername(request.getUsername());
    }

    public boolean checkEmailAvailability(@Valid EmailRequest request) {
        return userRepo.existsByEmailAddress(request.getEmail());
    }

    public UserResponsePayload fetchUserByEmail(@Valid EmailRequest request) {
        return userRepo.findUserByEmailAddress(request.getEmail())
                       .map(UserResponsePayload::new)
                       .orElseThrow(ResourceNotFoundException::new);
    }

    @Validated(OnCreate.class)
    public ResourceCreationResponse createUser(@Valid UserRequestPayload newUserRequest) {

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

    @Validated(OnUpdate.class)
    public void updateUser(@Valid UserRequestPayload updatedUserRequest) {

        User updatedUser = updatedUserRequest.extractResource();
        User userForUpdate = userRepo.findById(updatedUser.getId()).orElseThrow(ResourceNotFoundException::new);

        if (updatedUser.getFirstName() != null) {
            userForUpdate.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null) {
            userForUpdate.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getEmailAddress() != null) {
            if (userRepo.existsByEmailAddress(updatedUser.getEmailAddress())) {
                throw new ResourcePersistenceException("There is already a user with that email!");
            }
            userForUpdate.setEmailAddress(updatedUser.getEmailAddress());
        }

        if (updatedUser.getUsername() != null) {
            if (userRepo.existsByUsername(updatedUser.getUsername())) {
                throw new ResourcePersistenceException("There is already a user with that username!");
            }
            userForUpdate.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getPassword() != null) {
            userForUpdate.setPassword(updatedUser.getPassword());
        }

        if (updatedUser.getRole() != null) {
            userForUpdate.setRole(updatedUser.getRole());
        }

    }

    public UserResponsePayload authenticateUserCredentials(@Valid AuthRequest authRequest) {
        return userRepo.findUserByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword())
                       .map(UserResponsePayload::new)
                       .orElseThrow(AuthenticationException::new);
    }

}
