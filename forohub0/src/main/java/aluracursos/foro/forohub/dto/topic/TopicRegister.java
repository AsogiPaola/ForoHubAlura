package aluracursos.foro.forohub.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicRegister(
        @NotBlank
        String title,
        @NotBlank
        String message ,
        @NotNull
        Long authorId,
        @NotNull
        Long curseId
) {
}
