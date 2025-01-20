package aluracursos.foro.forohub.controllers;

import aluracursos.foro.forohub.dto.security.JWTtokenRecord;
import aluracursos.foro.forohub.dto.user.UserAutentication;
import aluracursos.foro.forohub.model.User;
import aluracursos.foro.forohub.security.Tokens;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Tokens tokens;


    @PostMapping
    public ResponseEntity autenticadeUser(@RequestBody @Valid UserAutentication userAutentication) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAutentication.email(), userAutentication.password());
        var userAutenticade = authenticationManager.authenticate(authToken);
        var JWTtoken = tokens.generateToken((User) userAutenticade.getPrincipal());
        return ResponseEntity.ok(new JWTtokenRecord(JWTtoken));
    }
}
