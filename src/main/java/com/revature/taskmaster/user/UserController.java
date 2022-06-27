package com.revature.taskmaster.user;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthorizationException;
import com.revature.taskmaster.common.util.web.security.Secured;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public List<UserResponsePayload> getAllUsers(@RequestHeader(value = "Authorization", required = false) String token) {
        return userService.fetchAllUsers();
    }

    @GetMapping("/search")
    public List<UserResponsePayload> findBy(@RequestParam Map<String, String> params) {
        return userService.search(params);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse postNewUser(@RequestBody UserRequestPayload newUserInfo) {
        return userService.createUser(newUserInfo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateUserInfo(@RequestBody UserRequestPayload updatedUserInfo) {
        userService.updateUser(updatedUserInfo);
    }

}
