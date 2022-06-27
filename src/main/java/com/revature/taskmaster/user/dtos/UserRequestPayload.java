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
        message = ValidatorMessageUtil.PROVIDE_NO_ID_ON_CREATE,
        groups = OnCreate.class)
    @NotNull(
        message = ValidatorMessageUtil.ID_REQUIRED_ON_UPDATE,
        groups = OnUpdate.class)
    private String id;

    @Length(
        message = ValidatorMessageUtil.FNAME_REQUIREMENTS,
        min = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(
        message = ValidatorMessageUtil.FNAME_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String firstName;

    @Length(
        message = ValidatorMessageUtil.LNAME_REQUIREMENTS,
        min = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(
        message = ValidatorMessageUtil.LNAME_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String lastName;

    @Email(
        message = ValidatorMessageUtil.EMAIL_REQUIREMENTS,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(
        message = ValidatorMessageUtil.EMAIL_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String email;

    @Length(
        message = ValidatorMessageUtil.USERNAME_REQUIREMENTS,
        min = 3,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(
        message = ValidatorMessageUtil.USERNAME_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String username;

    @Pattern(
        message = ValidatorMessageUtil.PASSWORD_REQUIREMENTS,
        regexp= RegexUtil.PASSWORD,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @NotNull(
        message = ValidatorMessageUtil.PASSWORD_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String password;

    @KnownRole(groups = OnUpdate.class)
    @Null(message = ValidatorMessageUtil.PROVIDE_NO_ROLE_ON_CREATE,
            groups = OnCreate.class)
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
