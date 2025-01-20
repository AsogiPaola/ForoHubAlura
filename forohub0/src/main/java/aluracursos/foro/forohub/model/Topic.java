package aluracursos.foro.forohub.model;


import aluracursos.foro.forohub.dto.topic.TopicRegister;
import aluracursos.foro.forohub.dto.topic.UpdateTopic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name= "topics")
@Entity(name="Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String message;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private State state = State.NO_RESPONSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curse_id")
    private Curse curse;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private List<Response> responses = new ArrayList<>();

    // Constructor principal
    public Topic(TopicRegister data, User author, Curse curse) {
        this.title = data.title();
        this.message = data.message();
        this.author = author;
        this.curse = curse;
        this.state = State.NO_SOLVE; // Estado inicial predeterminado
    }

    // Metodo para actualizar datos
    public void updateData(UpdateTopic updateTopic, User author, Curse curse) {
        if (updateTopic.title() != null) {
            this.title = updateTopic.title();
        }
        if (updateTopic.message() != null) {
            this.message = updateTopic.message();
        }
        if (updateTopic.state() != updateTopic.state()) {
            this.state = updateTopic.state();
        }
        if (author != null) {
            this.author = author;
        }
        if (curse != null) {
            this.curse = curse;
        }
    }

    // Agregar una respuesta y actualizar el estado
    public void addResponse(Response response) {
        this.responses.add(response);
        updateState(response.getSolution() ? State.SOLVE : State.NO_SOLVE);
    }

    // Cerrar el tópico
    public void closeTopic() {
        updateState(State.CLOSE);
    }

    // Metodo privado para actualizar el estado
    private void updateState(State newState) {
        if (newState != null && !newState.equals(this.state)) {
            this.state = newState;
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public State getState() {
        return state;
    }

    public User getAuthor() {
        return author;
    }

    public Curse getCurse() {
        return curse;
    }
}
