package com.revature.taskmaster.controllers;

import com.revature.taskmaster.dtos.NewUserRequest;
import com.revature.taskmaster.dtos.ResourceCreationResponse;
import com.revature.taskmaster.dtos.UserResponse;
import com.revature.taskmaster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public List<UserResponse> getAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/id/{userId}")
    public UserResponse getUserById(@PathVariable String userId) {
        return userService.fetchUserById(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse postNewUser(@RequestBody NewUserRequest newUser) {
        return userService.createUser(newUser);
    }

}
