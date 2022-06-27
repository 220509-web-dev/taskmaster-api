package com.revature.taskmaster.auth.dtos;

import com.revature.taskmaster.common.util.RegexUtil;
import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class AuthRequest {

    @Length(
        message = ValidatorMessageUtil.USERNAME_REQUIREMENTS,
        min = 3)
    private String username;

    @Pattern(
        message = ValidatorMessageUtil.PASSWORD_REQUIREMENTS,
        regexp = RegexUtil.PASSWORD)
    private String password;

}
