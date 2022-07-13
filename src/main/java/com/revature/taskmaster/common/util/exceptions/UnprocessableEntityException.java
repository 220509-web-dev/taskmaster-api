package com.revature.taskmaster.common.util.exceptions;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super("The provided information could not be properly processed");
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
