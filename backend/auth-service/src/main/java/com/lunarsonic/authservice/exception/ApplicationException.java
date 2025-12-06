package com.lunarsonic.authservice.exception;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationError applicationError;

    public ApplicationException(ApplicationError message) {
        super(message.getMessage());
        this.applicationError = message;
    }
}
