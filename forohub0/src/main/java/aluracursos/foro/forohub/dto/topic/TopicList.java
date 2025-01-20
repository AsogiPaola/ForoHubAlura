package aluracursos.foro.forohub.dto.topic;

import aluracursos.foro.forohub.model.Topic;

public record TopicList(Long id, String title, String message, String author, String curse) {
    public TopicList(Topic topic){
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getAuthor().getName(),
                topic.getCurse().getName());
    }
}
