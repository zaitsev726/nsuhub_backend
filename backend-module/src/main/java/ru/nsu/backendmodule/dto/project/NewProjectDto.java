package ru.nsu.backendmodule.dto.project;

import javax.validation.constraints.NotBlank;

public record NewProjectDto(
        @NotBlank
        String title,
        String preDescription,
        String description,
        @NotBlank
        Integer maxParticipantCount) {
}
