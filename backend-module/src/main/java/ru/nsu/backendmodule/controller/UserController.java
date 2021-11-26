package ru.nsu.backendmodule.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.nsu.backendmodule.dto.UserVerificationDto;
import ru.nsu.backendmodule.dto.UserVerificationRequestDto;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.dto.user.StudentInfoDto;
import ru.nsu.backendmodule.dto.user.EmailPasswordDto;
import ru.nsu.backendmodule.manager.UserManager;
import ru.nsu.backendshared.model.UserAuthorities;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserManager userManager;

    public UserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PostMapping("/verify/phone/request")
    //TODO
    public void requestPhoneVerification(@RequestBody UserVerificationRequestDto userVerificationRequestDto) {

    }

    @PostMapping("/verify/email/request")
    public void requestEmailVerification(@RequestBody UserVerificationRequestDto userVerificationRequestDto) {
        userManager.requestEmailVerification(userVerificationRequestDto);
    }

    @PostMapping("/verify")
    public CurrentUserDto verifyUser(@RequestBody UserVerificationDto userVerificationDto) {
        return userManager.verifyUser(userVerificationDto);
    }

    @PostMapping("/{userId}/password")
    @Secured({UserAuthorities.REGISTERED})
    public void updatePassword(@PathVariable String userId, @RequestBody EmailPasswordDto emailPasswordDto) {
        userManager.updatePassword(userId, emailPasswordDto);
    }

    @PostMapping("/{userId}/username")
    public CurrentUserDto updateUsername(@PathVariable String userId, @RequestParam String username) {
        return userManager.updateUsername(userId, username);
    }

    @PostMapping("/{userId}/bio")
    public CurrentUserDto updateStudentBio(@PathVariable String userId, @RequestBody StudentInfoDto studentInfoDto) {
        return userManager.updateStudentBio(userId, studentInfoDto);
    }
}
