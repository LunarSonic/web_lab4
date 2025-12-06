package com.lunarsonic.historyservice.exception;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum ApplicationError {
    NO_TOKEN("Нет токена", Response.Status.UNAUTHORIZED);

    private final String message;
    private final Response.Status status;

    ApplicationError(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }
}
