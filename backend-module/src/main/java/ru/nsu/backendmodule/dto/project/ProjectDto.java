package ru.nsu.backendmodule.dto.project;

import java.time.Instant;

public record ProjectDto(
        String id,
        String title,
        String preDescription,
        Integer maxParticipantsCount,
        Integer currentParticipantsCount,
        String createdBy,
        Instant createdAt,
        boolean isClosed) {
}
