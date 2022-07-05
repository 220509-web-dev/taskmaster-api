package com.revature.taskmaster.user.dtos;

import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UsernameRequest {

    @Length(message = ValidatorMessageUtil.USERNAME_REQUIREMENTS, min = 3)
    @NotNull(message = ValidatorMessageUtil.USERNAME_REQUIRED)
    private String username;

}
