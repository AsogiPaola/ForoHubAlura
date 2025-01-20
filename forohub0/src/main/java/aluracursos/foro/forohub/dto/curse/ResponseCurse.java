package aluracursos.foro.forohub.dto.curse;

import aluracursos.foro.forohub.model.Curse;

public record ResponseCurse(String name, String category) {
    public ResponseCurse(Curse curse){

        this(curse.getName(), curse.getCategory());
    }
}
