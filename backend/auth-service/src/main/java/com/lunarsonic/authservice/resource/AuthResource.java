package com.lunarsonic.authservice.resource;
import com.lunarsonic.authservice.dto.AuthRequest;
import com.lunarsonic.authservice.dto.MessageResponse;
import com.lunarsonic.authservice.dto.TokenResponse;
import com.lunarsonic.authservice.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {
    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    public Response register(AuthRequest request) {
        logger.info("Регистрируется пользователь: " + request.username());
        authService.register(request.username(), request.password());
        logger.info("Пользователь зарегистрирован");
        return Response.ok(new MessageResponse("Регистрация успешна")).build();
    }

    @POST
    @Path("/login")
    public Response login(AuthRequest request) {
        AuthService.Tokens tokens = authService.login(request.username(), request.password());
        logger.info("Пользователь " + request.username() + " зашел в систему");
        NewCookie newCookie = new NewCookie.Builder("refreshToken")
                .value(tokens.refreshToken())
                .path("/api")
                .sameSite(NewCookie.SameSite.STRICT)
                .httpOnly(true)
                .maxAge(30*24*60*60)
                .build();
        return Response.ok(new TokenResponse(tokens.accessToken())).cookie(newCookie).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh(@CookieParam("refreshToken") String refreshToken) {
        String newAccessToken = authService.refresh(refreshToken);
        return Response.ok(new TokenResponse(newAccessToken)).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("refreshToken") String refreshToken) {
        authService.logout(refreshToken);
        NewCookie clearedCookie = new NewCookie.Builder("refreshToken")
                .value("")
                .path("/api")
                .sameSite(NewCookie.SameSite.STRICT)
                .httpOnly(true)
                .maxAge(0)
                .build();
        return Response.ok(new MessageResponse("Пользователь успешно вышел")).cookie(clearedCookie).build();
    }
}

