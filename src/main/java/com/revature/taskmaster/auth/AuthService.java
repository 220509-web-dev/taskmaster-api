package com.revature.taskmaster.auth;

import com.revature.taskmaster.auth.dtos.AuthRequest;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.common.util.exceptions.AuthenticationException;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional
public class AuthService {

    private final AuthRepository authRepo;

    @Autowired
    public AuthService(AuthRepository authRepo) {
        this.authRepo = authRepo;
    }

    public Principal authenticateUserCredentials(@Valid AuthRequest authRequest) {
        return authRepo.findUserByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword())
                       .map(UserResponsePayload::new)
                       .map(Principal::new)
                       .orElseThrow(AuthenticationException::new);
    }

}
