package com.revature.taskmaster.test_utils;

import com.revature.taskmaster.auth.TokenService;
import com.revature.taskmaster.auth.dtos.Principal;
import com.revature.taskmaster.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MockTokenFactory {

    private final Map<User.Role, String> roleTokens = new HashMap<>();
    private final String unknownUserToken;

    @Autowired
    public MockTokenFactory(TokenService tokenService) {
        roleTokens.put(User.Role.ADMIN, tokenService.generateToken(new Principal("admin-user-id", "admin", "ADMIN")));
        roleTokens.put(User.Role.MANAGER, tokenService.generateToken(new Principal("manager-user-id", "manager", "MANAGER")));
        roleTokens.put(User.Role.DEV, tokenService.generateToken(new Principal("dev-user-id", "dev", "DEV")));
        roleTokens.put(User.Role.TESTER, tokenService.generateToken(new Principal("tester-user-id", "tester", "TESTER")));
        this.unknownUserToken = tokenService.generateToken(new Principal("unknown-user-id", "unknown", "ADMIN"));
    }

    public Map<User.Role, String> getRoleTokens() {
        return roleTokens;
    }

    public String getAdminToken() {
        return roleTokens.get(User.Role.ADMIN);
    }

    public String getManagerToken() {
        return roleTokens.get(User.Role.MANAGER);
    }

    public String getDevToken() {
        return roleTokens.get(User.Role.DEV);
    }

    public String getTesterToken() {
        return roleTokens.get(User.Role.TESTER);
    }

    public String getUnknownUserToken() {
        return unknownUserToken;
    }

}
