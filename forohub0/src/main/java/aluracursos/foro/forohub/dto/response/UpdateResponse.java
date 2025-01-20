package aluracursos.foro.forohub.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateResponse(
        @NotNull
        Long id,
        @NotBlank
        String message,
        @NotNull
        Long topicId,
        @NotBlank
        Long authorId,
        @NotNull
        Boolean solution
) {
}
