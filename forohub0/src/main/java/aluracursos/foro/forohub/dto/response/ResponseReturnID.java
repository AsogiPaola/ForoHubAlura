package aluracursos.foro.forohub.dto.response;

import aluracursos.foro.forohub.dto.topic.TopicResponse;
import aluracursos.foro.forohub.dto.topic.TopicResponseID;
import aluracursos.foro.forohub.dto.user.UserResponse;
import aluracursos.foro.forohub.model.Response;

public record ResponseReturnID(String message, TopicResponseID topic, String dateCreation, UserResponse author, String solution) {
    public ResponseReturnID(Response response) {
        this(response.getMessage(), new TopicResponseID(response.getTopic()), response.getDateCreation().toString(),
                new UserResponse(response.getAuthor()), response.getSolution().toString());
    }
}
