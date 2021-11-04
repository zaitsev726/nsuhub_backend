package ru.nsu.backendshared.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;

    public UserAuthenticationToken(String userId, Collection<String> authorities) {
        super(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        this.userId = userId;

        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isRegistered() {
        return hasAuthority(UserAuthorities.REGISTERED);
    }

    public boolean isVerified() {
        return hasAuthority(UserAuthorities.VERIFIED);
    }

    public boolean isFullyAuthenticated() {
        return hasAuthority(UserAuthorities.AUTHENTICATED);
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public boolean hasAuthority(String authority) {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(authority::equals);
    }
}
