package ru.nsu.backendmodule.dto.user;

import java.time.Instant;

public record UserDto(String id,
                      String email,
                      String phoneNumber,
                      Boolean isStudent,
                      StudentInfoDto studentInfoDto,
                      Instant createdAt) {
}
