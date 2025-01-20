package aluracursos.foro.forohub.controllers;


import aluracursos.foro.forohub.dto.response.*;
import aluracursos.foro.forohub.model.Response;
import aluracursos.foro.forohub.model.State;
import aluracursos.foro.forohub.model.Topic;
import aluracursos.foro.forohub.model.User;
import aluracursos.foro.forohub.repository.ResponseRepository;
import aluracursos.foro.forohub.repository.TopicRepository;
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
@RequestMapping("/response")
public class ResponseController {
    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ResponseController(ResponseRepository responseRepository, TopicRepository topicRepository, UserRepository userRepository){
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    // Registro de una nueva respuesta
    @PostMapping
    public ResponseEntity<ResponseReturn> register(@Valid @RequestBody ResponseRegister dataRegister,
                                                   UriComponentsBuilder uriBuilder) {
        return topicRepository.findById(dataRegister.topicId())
                .filter(topic -> topic.getState() != State.CLOSE)
                .map(topic -> {
                    User author = userRepository.findById(dataRegister.authorId())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    Response response = responseRepository.save(new Response(dataRegister, topic, author));
                    topic.addResponse(response);
                    URI location = uriBuilder.path("/responses/{id}").buildAndExpand(response.getId()).toUri();
                    return ResponseEntity.created(location).body(new ResponseReturn(response));
                })
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    // Listado paginado de respuestas
    @GetMapping
    public ResponseEntity<Page<ResponseList>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<ResponseList> responses = responseRepository.findAll(pageable).map(ResponseList::new);
        return ResponseEntity.ok(responses);
    }

    // Detalle de una respuesta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseReturnID> returnData(@PathVariable Long id) {
        return responseRepository.findById(id)
                .map(response -> ResponseEntity.ok(new ResponseReturnID(response)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualización de una respuesta
    @PutMapping
    @Transactional
    public ResponseEntity<ResponseReturn> update(@Valid @RequestBody UpdateResponse updateResponse) {
        return responseRepository.findById(updateResponse.id())
                .map(response -> {
                    Topic topic = topicRepository.findById(updateResponse.topicId())
                            .orElseThrow(() -> new RuntimeException("Topic not found"));
                    User author = userRepository.findById(updateResponse.authorId())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    response.updateData(updateResponse, topic, author);
                    return ResponseEntity.ok(new ResponseReturn(response));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminación de una respuesta por ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Response response = responseRepository.getReferenceById(id);
        responseRepository.delete(response);
        return ResponseEntity.noContent().build();
    }


}
