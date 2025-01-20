package aluracursos.foro.forohub.repository;

import aluracursos.foro.forohub.model.Curse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurseRepository extends JpaRepository<Curse, Long> {
}
