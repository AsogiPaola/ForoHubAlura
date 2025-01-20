package aluracursos.foro.forohub.model;


import aluracursos.foro.forohub.dto.response.ResponseRegister;
import aluracursos.foro.forohub.dto.response.UpdateResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private Boolean solution = false;

    // Constructor adicional con parámetros
    public Response(ResponseRegister datos, Topic topic, User author) {
        this.message = datos.message();
        this.topic = topic;
        this.author = author;
        this.solution = datos.solution();
        updateTopicState(topic, datos.solution());
    }

    // Metodo para actualizar datos
    public void updateData(UpdateResponse dataUpdate, Topic topic, User author) {
        if (dataUpdate.message() != null) {
            this.message = dataUpdate.message();
        }
        if (topic != null) {
            this.topic = topic;
        }
        if (author != null) {
            this.author = author;
        }
        if (dataUpdate.solution() != null && !dataUpdate.solution().equals(this.solution)) {
            this.solution = dataUpdate.solution();
            updateTopicState(topic, this.solution);
        }
    }

    // Actualización del estado del tópico asociado
    private void updateTopicState(Topic topic, Boolean solution) {
        if (topic != null) {
            topic.setState(solution ? State.SOLVE : State.NO_SOLVE);
        }
    }

    public Boolean getSolution() {
        return solution;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Topic getTopic() {
        return topic;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public User getAuthor() {
        return author;
    }
}
