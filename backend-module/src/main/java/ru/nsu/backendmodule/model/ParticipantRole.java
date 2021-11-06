package ru.nsu.backendmodule.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "participant_role")
public class ParticipantRole{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "experience")
    private String experience;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "participantRole")
    private final List<Participant> participants = new ArrayList<>();

    public ParticipantRole() {
    }

    public ParticipantRole(Integer count, String experience, Project project) {
        this.count = count;
        this.experience = experience;
        this.project = project;
    }

    public ParticipantRole(Integer count, String experience, String description, Project project) {
        this.count = count;
        this.experience = experience;
        this.description = description;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String roleDescription) {
        this.description = roleDescription;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant participant) {
        if (!participants.contains(participant)) {
            participants.add(participant);
        }
    }

    public boolean isParticipant(Participant participant) {
        return participants.stream().anyMatch(participant::equals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantRole that = (ParticipantRole) o;
        return Objects.equals(id, that.id) && Objects.equals(count, that.count) && Objects.equals(experience, that.experience) && Objects.equals(description, that.description) && Objects.equals(project, that.project) && Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
