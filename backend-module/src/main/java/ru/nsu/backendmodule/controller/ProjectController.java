package ru.nsu.backendmodule.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.nsu.backendmodule.dto.project.NewParticipantRoleDto;
import ru.nsu.backendmodule.dto.project.NewProjectDto;
import ru.nsu.backendmodule.dto.project.ProjectDetailedDto;
import ru.nsu.backendmodule.dto.project.ProjectDto;
import ru.nsu.backendmodule.service.ProjectService;
import ru.nsu.backendshared.model.UserAuthorities;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Secured({UserAuthorities.VERIFIED})
    public String createProject(@RequestBody NewProjectDto newProjectDto) {
        return projectService.createProject(
                newProjectDto.title(),
                newProjectDto.preDescription(),
                newProjectDto.description(),
                newProjectDto.maxParticipantCount());
    }

    @PostMapping("/{projectId}")
    @Secured({UserAuthorities.VERIFIED})
    public void createParticipantRole(
            @RequestBody NewParticipantRoleDto newParticipantRoleDto,
            @PathVariable String projectId) {
        projectService.createParticipantRole(
                newParticipantRoleDto.count(),
                newParticipantRoleDto.experience(),
                newParticipantRoleDto.description(),
                projectId);
    }

    @GetMapping
    public List<ProjectDto> getProjects(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam long startDate,
            @RequestParam long endDate) {
        var start = Instant.ofEpochMilli(startDate);
        var end = Instant.ofEpochMilli(endDate);

        return projectService.getClassrooms(page, size, start, end);
    }

    @GetMapping("/{projectId}")
    //TODO
    public ProjectDetailedDto getProjectDetailedInfo(@PathVariable String projectId) {
        return null;
    }

    @PostMapping("/accept/{participantId}/{projectId}/{roleId}")
    @Secured({UserAuthorities.VERIFIED})
    public void acceptParticipantToRole(
            @PathVariable String participantId,
            @PathVariable String projectId,
            @PathVariable Long roleId) {
        projectService.acceptParticipantToRole(participantId, projectId, roleId);
    }
}
