package NikKha03.TaskService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User extends ProjectOwner {

    // получаю данные пользователя из keycloak
    @NotNull
    String username;

    @NotNull
    String userId;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore()
    private List<Company> companyId;

}
