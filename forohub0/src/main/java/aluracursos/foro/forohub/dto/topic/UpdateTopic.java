package aluracursos.foro.forohub.dto.topic;

import aluracursos.foro.forohub.model.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopic(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        State state,
        @NotNull
        Long authorId,
        @NotNull
        Long curseId
) {
}
