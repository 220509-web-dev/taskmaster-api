package com.revature.taskmaster.user.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull
    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String role;

}
