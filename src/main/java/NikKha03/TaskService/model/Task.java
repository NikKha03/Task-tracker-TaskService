package NikKha03.TaskService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotNull
    private String header;

    private String comment;

    // кто создал задачу (id пользователя, полученный из keycloak)
    @NotNull
    private String creator;

    // исполнитель у задачи (id пользователя, полученный из keycloak)
    private List<String> implementers;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "tab", nullable = false)
    @JsonIgnore()
    private Tab tab;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime executionDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private Boolean isPause = false;

    private String tags;

    private String urlsObj;

    @OneToMany(mappedBy = "task")
    private List<Subtasks> subtasks;

}
