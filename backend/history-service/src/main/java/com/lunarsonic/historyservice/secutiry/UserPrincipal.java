package com.lunarsonic.historyservice.secutiry;
import lombok.Getter;
import java.security.Principal;

@Getter
public class UserPrincipal implements Principal {
    private final Long userId;

    public UserPrincipal(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return userId.toString();
    }
}
