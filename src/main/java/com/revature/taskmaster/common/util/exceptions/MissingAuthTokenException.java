package com.revature.taskmaster.common.util.exceptions;

public class MissingAuthTokenException extends AuthenticationException {

    public MissingAuthTokenException() {
        super("There was no authorization token found on the request");
    }

}
