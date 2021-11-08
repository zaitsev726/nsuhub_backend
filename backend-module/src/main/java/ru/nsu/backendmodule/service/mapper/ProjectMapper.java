package ru.nsu.backendmodule.service.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.backendmodule.dto.project.ParticipantRoleDto;
import ru.nsu.backendmodule.dto.project.ProjectDto;
import ru.nsu.backendmodule.model.ParticipantRole;
import ru.nsu.backendmodule.model.Project;

@Component
public class ProjectMapper {

    public ProjectDto mapToProjectDto(Project project, Integer currentCount) {
        return new ProjectDto(
                project.getId(),
                project.getTitle(),
                project.getPreDescription(),
                project.getMaxParticipantsCount(),
                currentCount,
                project.getCreatedBy().getName(),
                project.getCreatedAt(),
                project.getClosedAt() == null);
    }

    public ParticipantRoleDto mapToParticipantRoleDto(ParticipantRole role) {
        return new ParticipantRoleDto(
                role.getId(),
                role.getCount(),
                role.getParticipants().size(),
                role.getExperience(),
                role.getDescription());
    }
}
