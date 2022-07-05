package com.revature.taskmaster.user.dtos;

import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EmailRequest {

    @Email(message = ValidatorMessageUtil.EMAIL_REQUIREMENTS)
    @NotNull(message = ValidatorMessageUtil.EMAIL_REQUIRED)
    private String email;

}
