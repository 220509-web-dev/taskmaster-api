package com.revature.taskmaster.user.dtos;

import com.revature.taskmaster.common.util.RegexUtil;
import com.revature.taskmaster.common.util.web.validators.KnownRole;
import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.common.util.web.validators.groups.OnUpdate;
import com.revature.taskmaster.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserRequestPayload {

    @Null(
        message = ValidatorMessageUtil.PROVIDE_NO_ID_FOR_USER_CREATION,
        groups = OnCreate.class)
    @NotNull(
        message = ValidatorMessageUtil.ID_REQUIRED_FOR_USER_UPDATE,
        groups = OnUpdate.class)
    private String id;

    @Length(
        min = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(groups = OnCreate.class)
    private String firstName;

    @Length(
        min = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(groups = OnCreate.class)
    private String lastName;

    @Email(
        message = ValidatorMessageUtil.EMAIL_REQUIREMENTS,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(groups = OnCreate.class)
    private String email;

    @Length(
        message = ValidatorMessageUtil.USERNAME_REQUIREMENTS,
        min = 3,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(groups = OnCreate.class)
    private String username;

    @Pattern(
        message = ValidatorMessageUtil.PASSWORD_REQUIREMENTS,
        regexp= RegexUtil.PASSWORD,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(groups = OnCreate.class)
    private String password;

    @KnownRole(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private String role;

    public User extractResource() {

        if (id == null) {
            return new User(firstName, lastName, email, username, password);
        }

        if (role == null) {
            return new User(id, firstName, lastName, email, username, password);
        }

        return new User(id, firstName, lastName, email, username, password, User.Role.valueOf(role));
    }


}
