package aluracursos.foro.forohub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAutentication(
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email
) {
}
