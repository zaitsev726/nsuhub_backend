package ru.nsu.backendmodule.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.nsu.backendshared.model.UserAuthenticationToken;

import java.util.Optional;

public abstract class SecurityContextHelper {

    public static Optional<UserAuthenticationToken> getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UserAuthenticationToken) {
            return Optional.of((UserAuthenticationToken) auth);
        }

        return Optional.empty();
    }

    public static String getCurrentUserId() {
        return getCurrentUser()
                .map(UserAuthenticationToken::getUserId)
                .orElseThrow(() -> new AccessDeniedException("User not found in security context"));
    }

}
