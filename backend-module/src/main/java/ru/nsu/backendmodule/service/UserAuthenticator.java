package ru.nsu.backendmodule.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.nsu.backendshared.model.UserAuthenticationToken;

@Component
public class UserAuthenticator {
    private final AuthenticationManager authenticationManager;

    public UserAuthenticator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //TODO
    public UserAuthenticationToken authenticateByForm(String email, String password) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return (UserAuthenticationToken) auth;
    }
}
