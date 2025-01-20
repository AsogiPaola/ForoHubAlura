package aluracursos.foro.forohub.dto.response;

import aluracursos.foro.forohub.model.Response;

public record ResponseList(Long id, String name, String topic, String author) {
    public ResponseList(Response response){
        this(response.getId(), response.getMessage(), response.getTopic().getTitle(), response.getAuthor().getName());
    }
}
