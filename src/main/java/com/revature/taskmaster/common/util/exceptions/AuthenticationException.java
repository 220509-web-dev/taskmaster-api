package com.revature.taskmaster.common.util.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("No active user record found using the provided credentials!");
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
