package ru.nsu.backendmodule.dto.project;

import javax.validation.constraints.NotBlank;

public record ParticipantRoleDto(
        @NotBlank
        Long id,
        @NotBlank
        Integer count,
        @NotBlank
        Integer currentCount,
        @NotBlank
        String experience,
        String description) {
}
