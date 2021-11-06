package ru.nsu.backendmodule.controller;

import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.dto.user.UsernamePasswordDto;
import ru.nsu.backendmodule.dto.user.UuidDto;
import ru.nsu.backendmodule.service.UserAuthenticator;
import ru.nsu.backendshared.security.JwtTokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthenticator userAuthenticator;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(UserAuthenticator userAuthenticator) throws Exception {
        this.userAuthenticator = userAuthenticator;
        this.jwtTokenUtil = new JwtTokenUtil();
    }

    @PostMapping("/form")
    public CurrentUserDto authenticateByForm(
            @RequestBody UsernamePasswordDto usernamePasswordDto,
            HttpServletResponse httpServletResponse) throws JOSEException {
        var dto = userAuthenticator.authenticateByForm(usernamePasswordDto.username(), usernamePasswordDto.password());
        addCurrentUserAccessJwtToken(dto.id(), httpServletResponse);
        return dto;
    }

    @PostMapping("/uuid")
    public CurrentUserDto authenticateByUuid(
            @RequestBody UuidDto uuidDto,
            HttpServletResponse httpServletResponse) throws JOSEException {
        var dto = userAuthenticator.authenticateByUuid(uuidDto.uuid());
        addCurrentUserAccessJwtToken(dto.id(), httpServletResponse);
        return dto;
    }

    private void addCurrentUserAccessJwtToken(String userId, HttpServletResponse httpServletResponse) throws JOSEException {
        var accessToken = jwtTokenUtil.generateToken(userId, true);
        var cookie = new Cookie(jwtTokenUtil.getTokenName(), accessToken);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
