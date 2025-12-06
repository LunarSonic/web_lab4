package com.lunarsonic.authservice.exception;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum ApplicationError {
    INVALID_CREDENTIALS("Неверные учётные данные", Response.Status.UNAUTHORIZED),
    INVALID_TOKEN("Токен недействителен или истёк", Response.Status.UNAUTHORIZED),
    NO_TOKEN("Нет токена", Response.Status.UNAUTHORIZED);

    private final String message;
    private final Response.Status status;

    ApplicationError(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }
}
