package com.lunarsonic.geometryservice.resource;
import com.lunarsonic.geometryservice.secutiry.UserPrincipal;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

public abstract class BaseResource {

    @Context
    protected SecurityContext securityContext;

    protected Long getUserId() {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        return userPrincipal.getUserId();
    }

    protected String getToken() {
        UserPrincipal userPrincipal = (UserPrincipal) securityContext.getUserPrincipal();
        return userPrincipal.getToken();
    }
}
