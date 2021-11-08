package ru.nsu.backendmodule.dto.user;

public record CurrentUserDto(String id,
                             String name,
                             String email,
                             String phoneNumber,
                             boolean isStudent,
                             boolean authenticated,
                             boolean registered,
                             boolean verified) {
}
