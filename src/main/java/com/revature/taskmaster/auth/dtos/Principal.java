package com.revature.taskmaster.auth.dtos;

import com.revature.taskmaster.user.dtos.UserResponsePayload;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Principal {

    private String authUserId;
    private String authUserRole;

    public Principal(UserResponsePayload user) {
        this.authUserId = user.getId();
        this.authUserRole = user.getRole();
    }

    public Principal(String authUserId, String authUserRole) {
        this.authUserId = authUserId;
        this.authUserRole = authUserRole;
    }

}
