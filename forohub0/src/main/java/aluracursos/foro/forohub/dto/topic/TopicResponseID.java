package aluracursos.foro.forohub.dto.topic;

import aluracursos.foro.forohub.dto.curse.ResponseCurse;
import aluracursos.foro.forohub.dto.user.UserResponse;
import aluracursos.foro.forohub.model.Topic;

public record TopicResponseID(Long id, String title, String message, String dateCreation, String state, UserResponse author,
                              ResponseCurse responseCurse) {
    public TopicResponseID(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getDateCreation().toString(),
                topic.getState().toString(), new UserResponse(topic.getAuthor()),
                new ResponseCurse(topic.getCurse()));
    }
}
