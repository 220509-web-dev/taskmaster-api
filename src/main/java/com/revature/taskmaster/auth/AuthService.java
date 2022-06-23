package com.revature.taskmaster.auth;

import com.revature.taskmaster.auth.dtos.AuthRequest;
import com.revature.taskmaster.auth.dtos.AuthSuccessResponse;
import com.revature.taskmaster.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional
public class AuthService {

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public AuthSuccessResponse authenticate(@Valid AuthRequest authRequest) {
        return null;
    }

}
