package ru.nsu.backendmodule.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.dto.user.StudentInfoDto;
import ru.nsu.backendmodule.dto.user.EmailPasswordDto;
import ru.nsu.backendmodule.model.StudentInfo;
import ru.nsu.backendmodule.repository.UserRepository;
import ru.nsu.backendmodule.service.mapper.UserMapper;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void updateUsername(String userId, String username) {
        var user = userRepository.getById(userId);
        user.setName(username);
    }

    @Transactional
    public void updateStudentBio(String userId, StudentInfoDto studentInfoDto) {
        var user = userRepository.getById(userId);
        user.setStudentInfo(new StudentInfo(
                studentInfoDto.faculty(),
                studentInfoDto.direction(),
                studentInfoDto.course(),
                studentInfoDto.group()
        ));
    }

    @Transactional(readOnly = true)
    public CurrentUserDto getUser(String userId) {
        var user = userRepository.getById(userId);
        return userMapper.mapToCurrentUser(user, true);
    }

    @Transactional(readOnly = true)
    public boolean verifyEmail(String email) {
        var user = userRepository.findByEmailIgnoreCase(email);
        return user.isPresent();
    }

    @Transactional
    public void updateEmail(String userId, String email) {
        var user = userRepository.getById(userId);
        user.setEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean verifyPhone(String phone) {
        var user = userRepository.findByPhoneNumber(phone);
        return user.isPresent();
    }

    @Transactional
    public void updatePhone(String userId, String phoneNumber) {
        var user = userRepository.getById(userId);
        user.setPhoneNumber(phoneNumber);
    }

    @Transactional
    public void updatePassword(EmailPasswordDto emailPasswordDto) {
        var user = userRepository.findByEmailIgnoreCase(emailPasswordDto.email()).get();
        user.setPassword(bCryptPasswordEncoder.encode(emailPasswordDto.password()));
    }
}
