package com.revature.taskmaster.auth;

import com.revature.taskmaster.auth.dtos.AuthRequest;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.user.UserService;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Principal authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse resp) {
        Principal payload = authService.authenticateUserCredentials(authRequest);
        String token = tokenService.generateToken(payload);
        resp.setHeader("Authorization", token);
        return payload;
    }

}
