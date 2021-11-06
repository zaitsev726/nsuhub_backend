package ru.nsu.backendmodule.dto;

public record CurrentUserDto(String id,
                             String name,
                             String email,
                             String phoneNumber,
                             boolean authenticated,
                             boolean registered,
                             boolean verified) {
}
