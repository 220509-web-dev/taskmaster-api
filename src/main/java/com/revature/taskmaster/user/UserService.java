package com.revature.taskmaster.user;

import com.revature.taskmaster.common.datasource.EntitySearcher;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
public class UserService {

    private final UserRepository userRepo;
    private final EntitySearcher entitySearcher;

    @Autowired
    public UserService(UserRepository userRepo, EntitySearcher entitySearcher) {
        this.userRepo = userRepo;
        this.entitySearcher = entitySearcher;
    }

    public List<UserResponsePayload> fetchAllUsers() {
        return userRepo.findAll()
                       .stream()
                       .map(UserResponsePayload::new)
                       .collect(Collectors.toList());
    }

    public List<UserResponsePayload> search(Map<String, String> requestParamMap) {
        if (requestParamMap.isEmpty()) return fetchAllUsers();
        Set<User> matchingUsers = entitySearcher.searchForEntity(requestParamMap, User.class);
        if (matchingUsers.isEmpty()) throw new ResourceNotFoundException();
        return matchingUsers.stream()
                            .map(UserResponsePayload::new)
                            .collect(Collectors.toList());
    }

    public boolean isUsernameAvailable(@Valid UsernameRequest request) {
        return !userRepo.existsByUsername(request.getUsername());
    }

    public boolean isEmailAvailable(@Valid EmailRequest request) {
        return !userRepo.existsByEmailAddress(request.getEmail());
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

    public void activateUser(String userId) {
        userRepo.findById(userId)
                .orElseThrow(ResourceNotFoundException::new)
                .getMetadata()
                .setActive(true);
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

        userForUpdate.getMetadata().setUpdatedDatetime(LocalDateTime.now());

    }

    public void deactivateUser(String userId) {
        userRepo.findById(userId)
                .orElseThrow(ResourceNotFoundException::new)
                .getMetadata()
                .setActive(false);
    }

}
