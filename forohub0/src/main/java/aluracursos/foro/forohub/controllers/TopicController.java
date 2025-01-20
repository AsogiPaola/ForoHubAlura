package aluracursos.foro.forohub.controllers;


import aluracursos.foro.forohub.dto.topic.*;
import aluracursos.foro.forohub.model.Curse;
import aluracursos.foro.forohub.model.Topic;
import aluracursos.foro.forohub.model.User;
import aluracursos.foro.forohub.repository.CurseRepository;
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
@RequestMapping("/topics")
public class TopicController {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private CurseRepository curseRepository;

    public TopicController(TopicRepository topicRepository, UserRepository userRepository, CurseRepository curseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.curseRepository = curseRepository;
    }

    // Registro de un nuevo tema
    @PostMapping
    public ResponseEntity<TopicResponse> register(@RequestBody @Valid TopicRegister dataRegister,
                                                  UriComponentsBuilder uriBuilder) {
        User author = userRepository.findById(dataRegister.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Curse curse = curseRepository.findById(dataRegister.curseId())
                .orElseThrow(() -> new RuntimeException("Curse not found"));

        Topic topic = topicRepository.save(new Topic(dataRegister, author, curse));
        URI location = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(location).body(new TopicResponse(topic));
    }

    // Listado paginado de temas
    @GetMapping
    public ResponseEntity<Page<TopicList>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<TopicList> topics = topicRepository.findAll(pageable).map(TopicList::new);
        return ResponseEntity.ok(topics);
    }

    // Detalle de un tema por ID
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseID> returnData(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(topic -> ResponseEntity.ok(new TopicResponseID(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualización de un tema
    @PutMapping
    @Transactional
    public ResponseEntity<TopicResponse> update(@RequestBody @Valid UpdateTopic updateTopic) {
        Topic topic = topicRepository.findById(updateTopic.id())
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        User author = userRepository.findById(updateTopic.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Curse curse = curseRepository.findById(updateTopic.curseId())
                .orElseThrow(() -> new RuntimeException("Curse not found"));

        topic.updateData(updateTopic, author, curse);
        return ResponseEntity.ok(new TopicResponse(topic));
    }
    // Eliminación (cierre) de un tema
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.closeTopic();
        return ResponseEntity.noContent().build();
    }
}
