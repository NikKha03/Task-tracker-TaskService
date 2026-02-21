package NikKha03.TaskService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users_in_project")
public class UserInProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id пользователя, полученное из keycloak
    @NotNull
    private String keycloakId;

    @ManyToOne()
    @JoinColumn(name = "projectId", nullable = false)
    @JsonIgnore()
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_member_roles",
            joinColumns = @JoinColumn(name = "user_in_project_id"),
            inverseJoinColumns = @JoinColumn(name = "role_in_project_id"))
    private List<RolesInProject> roles = new ArrayList<>();

}
