package com.revature.taskmaster.user;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.common.util.exceptions.AuthorizationException;
import com.revature.taskmaster.common.util.web.security.Secured;
import com.revature.taskmaster.user.dtos.NewUserRequest;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Secured(allowedRoles = {"ADMIN"})
    @GetMapping(produces = "application/json")
    public List<UserResponse> getAllUsers(@RequestHeader(value = "Authorization", required = false) String token) {

        Principal requester = tokenService.extractTokenDetails(token);

        if (!requester.getAuthUserRole().equals("ADMIN")) {
            throw new AuthorizationException("You are not allowed to hit this endpoint based on your role!");
        }

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
