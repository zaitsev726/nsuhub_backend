package ru.nsu.backendmodule.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import ru.nsu.backendshared.model.UserUuidAuthenticationToken;

class UserUuidAuthProvider implements AuthenticationProvider {

    private final UserAuthService userAuthService;

    public UserUuidAuthProvider(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var token = (UserUuidAuthenticationToken) authentication;
        return userAuthService.authenticate(token.getUuid());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserUuidAuthenticationToken.class);
    }
}
