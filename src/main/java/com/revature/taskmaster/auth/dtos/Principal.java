package com.revature.taskmaster.auth.dtos;

import com.revature.taskmaster.user.User;
import com.revature.taskmaster.user.dtos.UserResponsePayload;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Principal {

    private String authUserId;
    private String authUsername;
    private String authUserRole;

    public Principal(UserResponsePayload user) {
        this.authUserId = user.getId();
        this.authUsername = user.getUsername();
        this.authUserRole = user.getRole();
    }

    public Principal(String authUserId, String authUsername, String authUserRole) {
        this.authUserId = authUserId;
        this.authUsername = authUsername;
        this.authUserRole = authUserRole;
    }

    public boolean isAdmin() {
        return this.authUserRole.equals(User.Role.ADMIN.name());
    }

    public boolean ownsResource(String resourceOwnerId) {
        return resourceOwnerId.equals(authUserId);
    }

}
