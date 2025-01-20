package aluracursos.foro.forohub.controllers;

import aluracursos.foro.forohub.dto.user.*;
import aluracursos.foro.forohub.model.User;
import aluracursos.foro.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Registro de un nuevo usuario
    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegister dataRegister,
                                                 UriComponentsBuilder uriBuilder) {
        User user = userRepository.save(new User(dataRegister));
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(new UserResponse(user));
    }

    // Listado paginado de usuarios
    @GetMapping
    public ResponseEntity<Page<UserList>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<UserList> users = userRepository.findAll(pageable).map(UserList::new);
        return ResponseEntity.ok(users);
    }
    // Detalle de un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseID> returnData(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UserResponseID(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualización de un usuario
    @PutMapping
    @Transactional
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UpdateUserData updateUserData) {
        return userRepository.findById(updateUserData.id())
                .map(user -> {
                    user.updateData(updateUserData);
                    return ResponseEntity.ok(new UserResponse(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
