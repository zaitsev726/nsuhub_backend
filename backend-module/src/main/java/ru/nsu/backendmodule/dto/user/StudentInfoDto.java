package ru.nsu.backendmodule.dto.user;

import javax.validation.constraints.NotBlank;

public record StudentInfoDto(
        @NotBlank String faculty,
        String direction,
        @NotBlank
        Integer course,
        String group) {
}
