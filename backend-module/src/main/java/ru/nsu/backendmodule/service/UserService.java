package ru.nsu.backendmodule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.dto.user.StudentInfoDto;
import ru.nsu.backendmodule.model.StudentInfo;
import ru.nsu.backendmodule.repository.UserRepository;
import ru.nsu.backendmodule.security.SecurityContextHelper;
import ru.nsu.backendmodule.service.mapper.UserMapper;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public CurrentUserDto updateUsername(String userId, String username) {
        var updatedBy = userRepository.getById(SecurityContextHelper.getCurrentUserId());

        if (!Objects.equals(userId, updatedBy.getId())) {
            throw new IllegalArgumentException("Not enough permissions to update username");
        }
        var user = userRepository.getById(userId);
        user.setName(username);

        return userMapper.mapToCurrentUser(user, true);
    }

    @Transactional
    public CurrentUserDto updateStudentBio(String userId, StudentInfoDto studentInfoDto) {
        var updatedBy = userRepository.getById(SecurityContextHelper.getCurrentUserId());

        if (!Objects.equals(userId, updatedBy.getId())) {
            throw new IllegalArgumentException("Not enough permissions to update username");
        }
        var user = userRepository.getById(userId);
        user.setStudentInfo(new StudentInfo(
                studentInfoDto.faculty(),
                studentInfoDto.direction(),
                studentInfoDto.course(),
                studentInfoDto.group()
        ));

        return userMapper.mapToCurrentUser(user, true);
    }
}
