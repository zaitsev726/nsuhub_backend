package ru.nsu.backendmodule.dto.project;

import javax.validation.constraints.NotBlank;

public record NewParticipantRoleDto(
        @NotBlank
        Integer count,
        @NotBlank
        String experience,
        String description) {
}
