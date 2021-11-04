package ru.nsu.backendmodule.security;

import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.backendshared.security.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final String headerPrefix = "Bearer ";
    private final JwtTokenUtil jwtTokenUtil;
    private final Logger logger;
    private final AuthenticationManager authenticationManager;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.logger = Logger.getLogger(JwtTokenFilter.class);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        var header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!header.startsWith(headerPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = header.split(" ")[1].trim();
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
