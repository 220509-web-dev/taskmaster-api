package com.revature.taskmaster.user.dtos;

import com.revature.taskmaster.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponsePayload {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public UserResponsePayload(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
    }

}