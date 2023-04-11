package ums.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String message, Throwable throwable) {
        super(message);
    }
}
