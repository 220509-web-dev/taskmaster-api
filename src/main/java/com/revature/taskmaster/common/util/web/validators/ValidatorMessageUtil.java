package com.revature.taskmaster.common.util.web.validators;

public class ValidatorMessageUtil {

    public static final String PROVIDE_NO_ID_ON_CREATE = "No id is expected in the payload for creation requests";
    public static final String ID_REQUIRED_ON_UPDATE = "An id is required in the payload for update requests";

    /* User Validation Error Message */
    public static final String FNAME_REQUIRED_ON_CREATE = "A first name value is expected in the request payload for user creation";
    public static final String LNAME_REQUIRED_ON_CREATE = "A last name value is expected in the request payload for user creation";
    public static final String EMAIL_REQUIRED_ON_CREATE = "An email value is expected in the request payload for user creation";
    public static final String USERNAME_REQUIRED_ON_CREATE = "A username value is expected in the request payload for user creation";
    public static final String PASSWORD_REQUIRED_ON_CREATE = "A password value is expected in the request payload for user creation";
    public static final String PROVIDE_NO_ROLE_ON_CREATE = "No role is expected in the request payload for user creation";
    public static final String FNAME_REQUIREMENTS = "At least one character must be provided for a first name";
    public static final String LNAME_REQUIREMENTS = "At least one character must be provided for a last name";
    public static final String EMAIL_REQUIREMENTS = "A valid email must be provided";
    public static final String USERNAME_REQUIREMENTS = "Usernames must contain at least three characters";
    public static final String PASSWORD_REQUIREMENTS = "Passwords must be eight characters long and contain at least one of the following: lowercase letter, uppercase letter, number, special character";
    public static final String EMAIL_REQUIRED = "A non-null email address is required for this operation";
    public static final String USERNAME_REQUIRED = "A non-null username is required for this operation";

    /* Task Validation Error Messages */
    public static final String TASK_TITLE_REQUIRED_ON_CREATE = "A title value is expected in the request payload for task creation";
    public static final String TASK_DESC_REQUIRED_ON_CREATE = "A description value is expected in the request payload for task creation";
    public static final String TASK_LABELS_REQUIRED_ON_CREATE = "At least one label string is expected in the request payload for task creation";
    public static final String TASK_TITLE_REQUIREMENTS = "Task titles must not be empty and no more than 50 characters long";
    public static final String TASK_DESC_REQUIREMENTS = "Task descriptions must not be empty strings";
    public static final String TASK_POINT_REQUIREMENTS = "Task point values must be in the inclusive range: 1 - 100";
    public static final String TASK_DUE_DATE_REQUIREMENTS = "Task due dates must be a date in the future";
    public static final String PROVIDE_NO_EXPLICIT_TASK_CREATOR_ON_CREATE = "An explicitly declared task creator is not excepted during task creation (it is inferred from the request token)";
    public static final String CANNOT_UPDATE_TASK_CREATOR = "The creator of a task cannot be updated";
    public static final String CREATOR_ID_REQUIREMENTS_ON_CREATE = "Task creation requests are expected to provide a creator id";

    private ValidatorMessageUtil() {
        super();
    }

}
