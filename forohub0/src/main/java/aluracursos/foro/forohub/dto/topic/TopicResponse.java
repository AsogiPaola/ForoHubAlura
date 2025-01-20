package aluracursos.foro.forohub.dto.topic;

import aluracursos.foro.forohub.model.Topic;

public record TopicResponse(String title, String message, String author, String curse) {
    public TopicResponse(Topic topic){
        this(topic.getTitle(), topic.getMessage(), topic.getAuthor().getName(), topic.getCurse().getName());
    }
}
