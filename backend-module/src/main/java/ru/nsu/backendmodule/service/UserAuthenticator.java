package ru.nsu.backendmodule.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.nsu.backendmodule.dto.CurrentUserDto;
import ru.nsu.backendmodule.security.UserAuthService;
import ru.nsu.backendshared.model.UserUuidAuthenticationToken;

@Component
public class UserAuthenticator {
    private final AuthenticationManager authenticationManager;
    private final UserAuthService userAuthService;

    public UserAuthenticator(AuthenticationManager authenticationManager,
                             UserAuthService userAuthService) {
        this.authenticationManager = authenticationManager;
        this.userAuthService = userAuthService;
    }

    public CurrentUserDto authenticateByForm(String email, String password) {
        try {
            var auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return userAuthService.getCurrentUser();

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("authentication by form failed");
        }
    }


    public CurrentUserDto authenticateByUuid(String uuid) {
        try {
            var auth = authenticationManager
                    .authenticate(new UserUuidAuthenticationToken(uuid));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return userAuthService.getCurrentUser();

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("authentication by uuid failed");
        }
    }
}
