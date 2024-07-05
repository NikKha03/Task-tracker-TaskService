package sharpBubbles.taskTracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "tasks")
public class Task {
    public Task() {}

    @Id
    @GeneratedValue
    private Long taskId;

    private String header;

    private String comment;

    // Если реализовывать командную работу
    private Long owner;

    @ManyToOne(cascade = CascadeType.ALL)
    private Status status;

    private LocalDateTime dateTimeOfTask;

    private LocalDateTime deadLineOfTask;

}
