package ru.nsu.backendmodule.security;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.backendshared.security.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final String headerPrefix = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;
    private final Logger logger;

    @Autowired //not constructor, because of cyclical dependency with SecurityConfig.java
    private AuthenticationManager authenticationManager;

    public JwtTokenFilter() throws Exception {
        this.jwtTokenUtil = new JwtTokenUtil();
        this.logger = Logger.getLogger(JwtTokenFilter.class);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        var cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            filterChain.doFilter(request, response);
            return;
        }

        var tokenCookie = Arrays
                .stream(cookies)
                .filter(cookie -> cookie.getName().equals(jwtTokenUtil.getTokenName()))
                .findAny();

        if (tokenCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = tokenCookie.get().getValue();
        try {
            if (!jwtTokenUtil.verifyToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            var parsedToken = jwtTokenUtil.parseToken(token);

            var authenticated =  authenticationManager.authenticate(parsedToken);
            if (authenticated != null && authenticated.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            }
        } catch (Exception e) {
            logger.log(Logger.Level.ERROR, "JWT Token " + token + " failed verification");
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
