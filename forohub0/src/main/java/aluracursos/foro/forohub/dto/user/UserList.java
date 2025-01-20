package aluracursos.foro.forohub.dto.user;

import aluracursos.foro.forohub.model.User;

public record UserList (Long id, String name, String email, String type){
    public UserList(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getType().toString());
    }
}
