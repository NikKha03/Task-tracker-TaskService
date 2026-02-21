package NikKha03.TaskService.repository;

import NikKha03.TaskService.model.ProjectOwner;
import NikKha03.TaskService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectOwnerRepository extends JpaRepository<ProjectOwner, Long> {
    @Query(value = "SELECT * FROM project_owners WHERE keycloak_id=:keycloakId", nativeQuery = true)
    User findByKeycloakId(String keycloakId);

    @Modifying
    @Query(value = "INSERT INTO project_owners (keycloak_id, dtype) VALUES (:keycloakId, 'User')", nativeQuery = true)
    void saveUser(String keycloakId);
}
