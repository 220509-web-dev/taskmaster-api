package com.revature.taskmaster.common.util.web.validators;

public class ValidatorMessageUtil {
    public static final String PROVIDE_NO_ID_FOR_USER_CREATION = "No id is expected in the request payload for user creation";
    public static final String ID_REQUIRED_FOR_USER_UPDATE = "An id is required in the request payload for user update";
    public static final String EMAIL_REQUIREMENTS = "A valid email must be provided";
    public static final String USERNAME_REQUIREMENTS = "Usernames must contain at least three characters";
    public static final String PASSWORD_REQUIREMENTS = "Passwords must be eight characters long and contain at least one of the following: lowercase letter, uppercase letter, number, special character";
}
