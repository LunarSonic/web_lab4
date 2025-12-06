package com.lunarsonic.geometryservice.secutiry;
import lombok.Getter;
import java.security.Principal;

@Getter
public class UserPrincipal implements Principal {
    private final Long userId;
    private final String token;

    public UserPrincipal(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public String getName() {
        return userId.toString();
    }
}
