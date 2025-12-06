package com.lunarsonic.geometryservice.exception;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum ApplicationError {
    POINT_ALREADY_EXISTS("Точка с данными координатами уже есть", Response.Status.CONFLICT),
    VALIDATION_FAILED("Ошибка валидации", Response.Status.BAD_REQUEST),
    NO_TOKEN("Нет токена", Response.Status.UNAUTHORIZED),
    NO_ACTIVE_POINT_GROUP("Нет активной группы результатов", Response.Status.CONFLICT),
    NO_POINT_GROUPS("Нет групп результатов", Response.Status.CONFLICT);

    private final String message;
    private final Response.Status status;

    ApplicationError(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }
}
