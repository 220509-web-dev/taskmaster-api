package com.revature.taskmaster.user;

import com.revature.taskmaster.common.util.exceptions.InvalidRequestException;
import com.revature.taskmaster.common.util.web.security.Secured;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.user.dtos.EmailRequest;
import com.revature.taskmaster.user.dtos.UserRequestPayload;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import com.revature.taskmaster.user.dtos.UsernameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured(allowedRoles = {"ADMIN"})
    @GetMapping(produces = "application/json")
    public List<UserResponsePayload> findBy(@RequestParam Map<String, String> params) {
        return userService.search(params);
    }

    @GetMapping("/availability")
    public ResponseEntity<Void> checkAvailability(@RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        if (username != null) {
            return userService.isUsernameAvailable(new UsernameRequest(username))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (email != null ) {
            return userService.isEmailAvailable(new EmailRequest(email))
                    ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        throw new InvalidRequestException("No email or username provided");
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

    @PatchMapping(value = "/activation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUser(@RequestParam String id) {
        userService.activateUser(id);
    }

    @DeleteMapping
    @Secured(allowedRoles = {"ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateUser(@RequestParam String id) {
        userService.deactivateUser(id);
    }

}
