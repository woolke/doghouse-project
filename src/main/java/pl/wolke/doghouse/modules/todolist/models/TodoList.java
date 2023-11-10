package pl.wolke.doghouse.modules.todolist.models;

import jakarta.persistence.*;
import lombok.*;
import pl.wolke.doghouse.modules.auth.models.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "todolists")
@AllArgsConstructor
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User user;

    private String color;

    private String title;

    @Enumerated(EnumType.STRING)
    private TodoListCategory category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
}
