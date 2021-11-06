package ru.nsu.backendmodule.dto.user;

public record CurrentUserDto(String id,
                             String name,
                             String email,
                             String phoneNumber,
                             boolean authenticated,
                             boolean registered,
                             boolean verified) {
}
