package ru.nsu.backendmodule.controller;

import org.springframework.web.bind.annotation.*;
import ru.nsu.backendmodule.dto.UsernamePasswordDto;
import ru.nsu.backendmodule.service.UserAuthenticator;
import ru.nsu.backendshared.model.UserAuthenticationToken;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthenticator userAuthenticator;

    public AuthController(UserAuthenticator userAuthenticator) {
        this.userAuthenticator = userAuthenticator;
    }

    @GetMapping("/form")
    public UserAuthenticationToken authenticateByForm(@RequestBody UsernamePasswordDto usernamePasswordDto){
        return userAuthenticator.authenticateByForm(usernamePasswordDto.username(), usernamePasswordDto.password());
    }
}
