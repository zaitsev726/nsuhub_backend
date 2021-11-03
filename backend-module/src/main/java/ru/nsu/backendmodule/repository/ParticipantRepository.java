package ru.nsu.backendmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.backendmodule.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
