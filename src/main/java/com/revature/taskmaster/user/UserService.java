package com.revature.taskmaster.user;

import com.revature.taskmaster.user.dtos.NewUserRequest;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.UserResponse;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.common.util.exceptions.ResourcePersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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

//        List<User> users = userRepo.findAll();
//        List<UserResponse> result = new ArrayList<>();
//
//        for (User user : users) {
//            result.add(new UserResponse(user));
//        }
//
//        return result;

        // equivalent to the above code
        return userRepo.findAll()
                       .stream()
                       .map(UserResponse::new)
                       .collect(Collectors.toList());
    }

    public UserResponse fetchUserById(String id) {
        return userRepo.findById(id)
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

}
