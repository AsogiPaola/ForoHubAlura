package aluracursos.foro.forohub.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseRegister(
        @NotBlank
        String message,
        @NotNull
        Long topicId,
        @NotNull
        Long authorId,
        @NotBlank
        Boolean solution
) {
}
