package ru.nsu.backendmodule.service.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.backendmodule.dto.user.CurrentUserDto;
import ru.nsu.backendmodule.model.User;

@Component
public class UserMapper {
    public CurrentUserDto mapToCurrentUser(User user, boolean authenticated) {
        return new CurrentUserDto(user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber(), authenticated, user.isRegistered(), user.isVerified());
    }
}
