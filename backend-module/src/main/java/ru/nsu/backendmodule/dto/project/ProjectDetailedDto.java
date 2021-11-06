package ru.nsu.backendmodule.dto.project;

import java.time.Instant;
import java.util.List;

public record ProjectDetailedDto(
        String id,
        String title,
        String description,
        Integer maxParticipantsCount,
        Integer currentParticipantsCount,
        String createdBy,
        Instant createdAt,
        Instant startedAt,
        Instant closedAt,
        List<ParticipantRoleDto> participantRoles
) {
}
