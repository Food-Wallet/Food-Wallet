package com.foodwallet.server.common.exception;

public class AuthenticationException extends IllegalArgumentException {

    public AuthenticationException() {
    }

    public AuthenticationException(String s) {
        super(s);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
