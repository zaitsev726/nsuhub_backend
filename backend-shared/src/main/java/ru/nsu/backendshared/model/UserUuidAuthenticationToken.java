package ru.nsu.backendshared.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

public class UserUuidAuthenticationToken extends AbstractAuthenticationToken {
    private final String uuid;

    public UserUuidAuthenticationToken(String uuid) {
        super(null);

        this.uuid = uuid;
        super.setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }


}
