package ru.nsu.backendmodule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.backendmodule.dto.project.ProjectDetailedDto;
import ru.nsu.backendmodule.dto.project.ProjectDto;
import ru.nsu.backendmodule.model.Participant;
import ru.nsu.backendmodule.model.ParticipantRole;
import ru.nsu.backendmodule.model.Project;
import ru.nsu.backendmodule.repository.ParticipantRepository;
import ru.nsu.backendmodule.repository.ParticipantRoleRepository;
import ru.nsu.backendmodule.repository.ProjectRepository;
import ru.nsu.backendmodule.repository.UserRepository;
import ru.nsu.backendmodule.security.SecurityContextHelper;
import ru.nsu.backendmodule.service.mapper.ProjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ParticipantRoleRepository participantRoleRepository;
    private final ParticipantRepository participantRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          ParticipantRoleRepository participantRoleRepository,
                          ParticipantRepository participantRepository,
                          ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.participantRoleRepository = participantRoleRepository;
        this.participantRepository = participantRepository;
        this.projectMapper = projectMapper;
    }

    @Transactional
    public String createProject(String title, String preDescription, String description, Integer maxParticipant) {
        var createdBy = userRepository.getById(SecurityContextHelper.getCurrentUserId());
        var project = projectRepository.save(new Project(title, preDescription, description, createdBy, maxParticipant));

        return project.getId();
    }

    @Transactional
    public void createParticipantRole(Integer count, String experience, String description, String projectId) {
        var project = projectRepository.getById(projectId);
        participantRoleRepository.save(new ParticipantRole(count, experience, description, project));
    }

    @Transactional(readOnly = true)
    //TODO
    public List<ProjectDto> getClassrooms(int page, int size, Instant start, Instant end) {
        return null;
    }

    @Transactional
    public void acceptParticipantToRole(String participantId, String projectId, Long roleId) {
        var project = projectRepository.getById(projectId);
        if (project.isProjectOwner(SecurityContextHelper.getCurrentUserId())) {
            throw new IllegalArgumentException("User is not project owner");
        }
        var participant = userRepository.getById(participantId);
        var role = participantRoleRepository.getById(roleId);

        if (!project.getParticipantRoles().contains(role)) {
            throw new IllegalArgumentException("Project does not have such role");
        }
        participantRepository.save(new Participant(participant, project, role));
    }

    @Transactional(readOnly = true)
    public ProjectDetailedDto getProjectDetailedInfo(String projectId) {
        var project = projectRepository.getById(projectId);
        var currentParticipantsCount = project
                .getParticipantRoles()
                .stream()
                .reduce(0, (subtotal, element) -> subtotal + element.getParticipants().size(), Integer::sum);

        return new ProjectDetailedDto(
                projectId, project.getTitle(), project.getDescription(),
                project.getMaxParticipantsCount(), currentParticipantsCount,
                project.getCreatedBy().getId(), project.getCreatedAt(),
                project.getStartedAt(), project.getClosedAt(),
                project.getParticipantRoles().stream().map(projectMapper::mapToParticipantRoleDto).collect(Collectors.toList()));
    }
}
