package com.lunarsonic.geometryservice.exception.mapper;
import com.lunarsonic.geometryservice.dto.MessageResponse;
import com.lunarsonic.geometryservice.exception.ApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException e) {
        Response.Status status = e.getApplicationError().getStatus();
        return Response.status(status).entity(new MessageResponse(e.getMessage())).build();
    }
}
