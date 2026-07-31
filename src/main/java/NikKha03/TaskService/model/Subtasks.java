package NikKha03.TaskService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
@Table(name = "subtasks")
public class Subtasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subtaskId;

    @NotNull
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task", nullable = false)
    private Task task;

    // кто выполнил задачу (id пользователя, полученный из keycloak)
    private String implementer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

}
