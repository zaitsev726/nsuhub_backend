package ru.nsu.backendmodule.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.nsu.backendmodule.dto.UserVerificationDto;
import ru.nsu.backendmodule.dto.UserVerificationRequestDto;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendshared.model.UserAuthorities;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController() {

    }

    @PostMapping("/verify/phone/request")
    //TODO
    public void requestPhoneVerification(@RequestBody UserVerificationRequestDto userVerificationRequestDto) {

    }

    @PostMapping("/verify/email/request")
    //TODO
    public void requestEmailVerification(@RequestBody UserVerificationRequestDto userVerificationRequestDto) {

    }

    @PostMapping("/verify")
    //TODO
    public CurrentUserDto verifyUser(@RequestBody UserVerificationDto userVerificationDto) {
        return null;
    }

    @PostMapping("/{userId}/password")
    @Secured({UserAuthorities.REGISTERED})
    //TODO create method (add UsernamePasswordDto)
    public void updatePassword(@PathVariable String userId) {

    }

    @PostMapping("/{userId}/username")
    //TODO
    public CurrentUserDto updateUsername(
            @PathVariable String userId,
            @RequestParam String username) {
        return null;
    }
}
