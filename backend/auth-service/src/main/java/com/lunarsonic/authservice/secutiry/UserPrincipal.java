package com.lunarsonic.authservice.secutiry;
import com.lunarsonic.authservice.entity.User;
import java.security.Principal;

public record UserPrincipal(User user) implements Principal {
    @Override
    public String getName() {
        return user.getUsername();
    }
}
