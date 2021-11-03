package ru.nsu.backendmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.backendmodule.model.ParticipantRole;

@Repository
public interface ParticipantRoleRepository extends JpaRepository<ParticipantRole, Long> {
}
