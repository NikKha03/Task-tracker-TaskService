package NikKha03.TaskService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User extends ProjectOwner {

    @NotNull
    String keycloakId;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore()
    private List<Company> companyId;

}
