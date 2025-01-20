package aluracursos.foro.forohub.dto.user;

import aluracursos.foro.forohub.model.Type;

public record UpdateUserData(Long id, String name, String email, String password, Type type) {
}
