package ru.nsu.backendmodule.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

class UsernamePasswordAuthProvider implements AuthenticationProvider {
    private final UserAuthService userAuthService;

    UsernamePasswordAuthProvider(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = (UsernamePasswordAuthenticationToken) authentication;
        return userAuthService.authenticate((String) token.getPrincipal(),(String) token.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
