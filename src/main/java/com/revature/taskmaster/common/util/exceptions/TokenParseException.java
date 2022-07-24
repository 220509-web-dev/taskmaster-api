package com.revature.taskmaster.common.util.exceptions;

public class TokenParseException extends AuthenticationException {

    public TokenParseException(String message) {
        super(message);
    }

    public TokenParseException(Throwable cause) {
        super("The provided token could not be parsed", cause);
    }

    public TokenParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
