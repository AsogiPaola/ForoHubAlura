package aluracursos.foro.forohub.repository;

import aluracursos.foro.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
