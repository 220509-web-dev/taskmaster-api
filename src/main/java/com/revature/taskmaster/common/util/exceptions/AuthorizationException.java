package com.revature.taskmaster.common.util.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("You do not have the proper permissions to perform that operation.");
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
