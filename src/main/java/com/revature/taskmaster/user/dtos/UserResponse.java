package com.revature.taskmaster.user.dtos;

import com.revature.taskmaster.task.dtos.TaskResponse;
import com.revature.taskmaster.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
    }

}
