package aluracursos.foro.forohub.dto.response;

import aluracursos.foro.forohub.model.Response;

public record ResponseReturn(String message, String topic, String author) {
    public ResponseReturn(Response response){
        this(response.getMessage(), response.getTopic().getTitle(), response.getAuthor().getName() );
    }
}
