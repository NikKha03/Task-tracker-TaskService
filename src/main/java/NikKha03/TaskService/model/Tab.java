package NikKha03.TaskService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tabs")
public class Tab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabId;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "projectId", nullable = false)
    @JsonIgnore
    private Project project;

    @OneToMany(mappedBy = "tab", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks;

}
