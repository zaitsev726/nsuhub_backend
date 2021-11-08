package ru.nsu.backendmodule.manager;

import org.springframework.stereotype.Component;
import ru.nsu.backendmodule.dto.UserVerificationRequestDto;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.dto.user.StudentInfoDto;
import ru.nsu.backendmodule.email.EmailService;
import ru.nsu.backendmodule.security.SecurityContextHelper;
import ru.nsu.backendmodule.service.UserService;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class UserManager {

    private final UserService userService;
    private final EmailService emailService;
    private final Collection<EmailCodes> emailCodes = new ConcurrentLinkedQueue<>();
    private final Random random = new Random();

    public UserManager(UserService userService, EmailService emailService) {

        this.userService = userService;
        this.emailService = emailService;
    }

    public CurrentUserDto updateUsername(String userId, String username) {
        var currentUserId = SecurityContextHelper.getCurrentUserId();

        if (!Objects.equals(userId, currentUserId)) {
            throw new IllegalArgumentException("Not enough permissions to update username");
        }
        userService.updateUsername(userId, username);
        return userService.getUser(userId);
    }

    public CurrentUserDto updateStudentBio(String userId, StudentInfoDto studentInfoDto) {
        var currentUserId = SecurityContextHelper.getCurrentUserId();

        if (!Objects.equals(userId, currentUserId)) {
            throw new IllegalArgumentException("Not enough permissions to update username");
        }
        userService.updateStudentBio(userId, studentInfoDto);
        return userService.getUser(userId);
    }

    public void requestEmailVerification(UserVerificationRequestDto userVerificationRequestDto) {
        var email = userVerificationRequestDto.phoneOrEmail();

        if (userService.verifyEmail(email)) {
            throw new IllegalArgumentException("Your email is invalid");
        }

        var userId = SecurityContextHelper.getCurrentUserId();
        emailCodes.removeIf(verifyCode -> Objects.equals(verifyCode.userId, userId));

        var generatedCode = 1000 + random.nextInt(8999);
        emailCodes.add(new EmailCodes(userId, email, String.valueOf(generatedCode)));
        emailService.sendSimpleMessage(email, "Код подтверждения", "Код подтверждения - " + generatedCode);
    }

    private record EmailCodes(String userId, String email, String code) {

    }
}
