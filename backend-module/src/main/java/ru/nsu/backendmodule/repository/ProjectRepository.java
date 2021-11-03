package ru.nsu.backendmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.backendmodule.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
}
