package aluracursos.foro.forohub.dto.user;

import aluracursos.foro.forohub.model.User;

public record UserResponse(String name, String email, String type) {
    public UserResponse(User user){
        this(user.getName(), user.getEmail(), user.getType().toString());
    }
}
