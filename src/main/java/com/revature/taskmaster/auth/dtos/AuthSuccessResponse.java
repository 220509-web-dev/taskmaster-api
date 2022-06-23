package com.revature.taskmaster.auth.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthSuccessResponse {

    private String authUserId;
    private String authUserRole;
    private String resourceAccessToken;

}
