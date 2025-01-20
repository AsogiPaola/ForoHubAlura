package aluracursos.foro.forohub.dto.user;

import aluracursos.foro.forohub.model.User;

public record UserResponseID(String name, String email, String type, String password) {
    public UserResponseID(User user){
        this(user.getName(), user.getEmail(), user.getPassword(), user.getType().toString());
    }
}