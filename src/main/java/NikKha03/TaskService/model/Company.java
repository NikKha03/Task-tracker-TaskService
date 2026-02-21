package NikKha03.TaskService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Reference;

import java.util.List;

@Entity
public class Company extends ProjectOwner {

    @NotNull
    String companyName;

    @NotNull
    @ManyToOne()
    ProjectOwner ownerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employees_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    List<User> employees;

}
